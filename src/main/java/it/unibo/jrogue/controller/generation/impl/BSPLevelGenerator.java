package it.unibo.jrogue.controller.generation.impl;

import it.unibo.jrogue.commons.Position;
import it.unibo.jrogue.controller.generation.api.BSPNode;
import it.unibo.jrogue.controller.generation.api.GenerationConfig;
import it.unibo.jrogue.controller.generation.api.LevelGenerator;
import it.unibo.jrogue.entity.GameRandom;
import it.unibo.jrogue.entity.world.api.Hallway;
import it.unibo.jrogue.entity.world.api.Level;
import it.unibo.jrogue.entity.world.api.Room;
import it.unibo.jrogue.entity.world.api.Tile;
import it.unibo.jrogue.entity.world.impl.SimpleGameMap;
import it.unibo.jrogue.entity.world.impl.SimpleHallway;
import it.unibo.jrogue.entity.world.impl.SimpleLevel;
import it.unibo.jrogue.entity.world.impl.SimpleRoom;
import java.util.ArrayList;
import java.util.List;

/**
 * BSP-based dungeon level generator.
 * Uses Binary Space Partitioning to create rooms and connect them with corridors.
 */
public final class BSPLevelGenerator implements LevelGenerator {

    private static final double ROOM_PADDING = 0.20;
    private static final double SPLIT_RATIO_MIN = 0.35;
    private static final double SPLIT_RATIO_MAX = 0.65;
    private static final double ASPECT_RATIO_THRESHOLD = 1.25;

    @Override
    public void setSeed(final long seed) {
        GameRandom.setSeed(seed);
    }

    @Override
    public Level generate(final GenerationConfig config) {
        GameRandom.setSeed(config.seed());

        // Step 1: Create the BSP tree
        final BSPNodeImpl root = createBSPTree(
            new Position(1, 1),
            config.mapWidth() - 2,
            config.mapHeight() - 2,
            0,
            config.maxDepth(),
            config.minPartitionSize()
        );

        // Step 2: Place rooms in leaf nodes
        final List<Room> rooms = placeRooms(root, config);

        // Step 3: Connect rooms with hallways
        final List<Hallway> hallways = connectRooms(root);

        // Step 4: Create the tile grid
        final Tile[][] tiles = createTileGrid(config, rooms, hallways);

        // Step 5: Place stairs and get startPos
        final Position startPos = rooms.get(0).getCenter();
        Position stairsPos = null;
        if (rooms.size() > 1) {
            stairsPos = rooms.get(rooms.size() - 1).getCenter();
            tiles[stairsPos.y()][stairsPos.x()] = Tile.STAIRS_UP;
        }

        // Step 6: Create and return the level
        final SimpleGameMap map = new SimpleGameMap(tiles, rooms, hallways, startPos, stairsPos);

        return new SimpleLevel(map, config.levelNumber());
    }

    private BSPNodeImpl createBSPTree(
            final Position topLeft,
            final int width,
            final int height,
            final int depth,
            final int maxDepth,
            final int minSize) {

        // Base case: stop if too small or max depth reached
        if (depth >= maxDepth) {
            return new BSPNodeImpl(topLeft, width, height);
        }

        // Decide split direction based on aspect ratio
        final boolean canSplitHorizontal = height >= minSize * 2;
        final boolean canSplitVertical = width >= minSize * 2;

        if (!canSplitHorizontal && !canSplitVertical) {
            return new BSPNodeImpl(topLeft, width, height);
        }

        final boolean horizontal;
        if (canSplitHorizontal && canSplitVertical) {
            // Prefer splitting the longer dimension
            if ((double) width / height >= ASPECT_RATIO_THRESHOLD) {
                horizontal = false;
            } else if ((double) height / width >= ASPECT_RATIO_THRESHOLD) {
                horizontal = true;
            } else {
                horizontal = GameRandom.nextBoolean();
            }
        } else {
            horizontal = canSplitHorizontal;
        }

        if (horizontal) {
            return splitHorizontally(topLeft, width, height, depth, maxDepth, minSize);
        } else {
            return splitVertically(topLeft, width, height, depth, maxDepth, minSize);
        }
    }

    private BSPNodeImpl splitHorizontally(
            final Position topLeft,
            final int width,
            final int height,
            final int depth,
            final int maxDepth,
            final int minSize) {

        final int splitRange = (int) ((SPLIT_RATIO_MAX - SPLIT_RATIO_MIN) * height);
        final int splitOffset = (int) (SPLIT_RATIO_MIN * height) + (splitRange > 0 ? GameRandom.nextInt(splitRange) : 0);
        final int splitY = Math.max(minSize, Math.min(height - minSize, splitOffset));

        final BSPNodeImpl left = createBSPTree(
            topLeft, width, splitY, depth + 1, maxDepth, minSize
        );
        final BSPNodeImpl right = createBSPTree(
            new Position(topLeft.x(), topLeft.y() + splitY),
            width, height - splitY, depth + 1, maxDepth, minSize
        );

        return new BSPNodeImpl(topLeft, width, height, left, right, true);
    }

    private BSPNodeImpl splitVertically(
            final Position topLeft,
            final int width,
            final int height,
            final int depth,
            final int maxDepth,
            final int minSize) {

        final int splitRange = (int) ((SPLIT_RATIO_MAX - SPLIT_RATIO_MIN) * width);
        final int splitOffset = (int) (SPLIT_RATIO_MIN * width) + (splitRange > 0 ? GameRandom.nextInt(splitRange) : 0);
        final int splitX = Math.max(minSize, Math.min(width - minSize, splitOffset));

        final BSPNodeImpl left = createBSPTree(
            topLeft, splitX, height, depth + 1, maxDepth, minSize
        );
        final BSPNodeImpl right = createBSPTree(
            new Position(topLeft.x() + splitX, topLeft.y()),
            width - splitX, height, depth + 1, maxDepth, minSize
        );

        return new BSPNodeImpl(topLeft, width, height, left, right, false);
    }

    private List<Room> placeRooms(final BSPNodeImpl root, final GenerationConfig config) {
        final List<Room> rooms = new ArrayList<>();
        collectRoomsFromLeaves(root, rooms, config);
        return rooms;
    }

    private void collectRoomsFromLeaves(
            final BSPNodeImpl node,
            final List<Room> rooms,
            final GenerationConfig config) {

        if (node.isLeaf()) {
            final Room room = createRoomInPartition(node, config);
            if (room != null) {
                node.setRoom(room);
                rooms.add(room);
            }
        } else {
            node.getLeftChild().ifPresent(child ->
                collectRoomsFromLeaves((BSPNodeImpl) child, rooms, config)
            );
            node.getRightChild().ifPresent(child ->
                collectRoomsFromLeaves((BSPNodeImpl) child, rooms, config)
            );
        }
    }

    private Room createRoomInPartition(final BSPNode node, final GenerationConfig config) {
        final int padding = (int) (Math.min(node.getWidth(), node.getHeight()) * ROOM_PADDING);
        final int availableWidth = node.getWidth() - padding * 2;
        final int availableHeight = node.getHeight() - padding * 2;

        if (availableWidth < config.minRoomSize() || availableHeight < config.minRoomSize()) {
            return null;
        }

        final int maxW = Math.min(config.maxRoomSize(), availableWidth);
        final int maxH = Math.min(config.maxRoomSize(), availableHeight);
        final int minW = config.minRoomSize();
        final int minH = config.minRoomSize();

        final int roomW = minW + (maxW > minW ? GameRandom.nextInt(maxW - minW + 1) : 0);
        final int roomH = minH + (maxH > minH ? GameRandom.nextInt(maxH - minH + 1) : 0);

        final int offsetX = padding + (availableWidth > roomW ? GameRandom.nextInt(availableWidth - roomW + 1) : 0);
        final int offsetY = padding + (availableHeight > roomH ? GameRandom.nextInt(availableHeight - roomH + 1) : 0);

        final Position roomTopLeft = new Position(
            node.getTopLeft().x() + offsetX,
            node.getTopLeft().y() + offsetY
        );

        return new SimpleRoom(roomTopLeft, roomW, roomH);
    }

    private List<Hallway> connectRooms(final BSPNode root) {
        final List<Hallway> hallways = new ArrayList<>();
        // 
        connectNode(root, hallways);
        return hallways;
    }

    private Room connectNode(final BSPNode node, final List<Hallway> hallways) {
        if (node.isLeaf()) {
            return node.getRoom().orElse(null);
        }

        // Connect children (recursive)
        Room leftRoom = null;
        Room rightRoom = null;

        if (node.getLeftChild().isPresent()) {
            leftRoom = connectNode(node.getLeftChild().get(), hallways);
        }
        if (node.getRightChild().isPresent()) {
            rightRoom = connectNode(node.getRightChild().get(), hallways);
        }

        // Connect left subtree to right subtree using closest room pair each time
        if (node.getLeftChild().isPresent() && node.getRightChild().isPresent()) {
            // This is not a optimized algorithm but for small number of nodes is ok
            final List<Room> leftRooms = collectAllRooms(node.getLeftChild().get());
            final List<Room> rightRooms = collectAllRooms(node.getRightChild().get());

            if (!leftRooms.isEmpty() && !rightRooms.isEmpty()) {
                final Room closest0 = findClosestLeft(leftRooms, rightRooms);
                final Room closest1 = findClosestRight(leftRooms, rightRooms);
                hallways.add(createHallway(closest0, closest1));
                return closest0;
            }
        }

        return leftRoom != null ? leftRoom : rightRoom;
    }

    private List<Room> collectAllRooms(final BSPNode node) {
        final List<Room> rooms = new ArrayList<>();
        if (node.isLeaf()) {
            node.getRoom().ifPresent(rooms::add);
        } else {
            node.getLeftChild().ifPresent(c -> rooms.addAll(collectAllRooms(c)));
            node.getRightChild().ifPresent(c -> rooms.addAll(collectAllRooms(c)));
        }
        return rooms;
    }

    private Room findClosestLeft(final List<Room> leftRooms, final List<Room> rightRooms) {
        Room bestLeft = leftRooms.get(0);
        int bestDist = Integer.MAX_VALUE;
        for (final Room left : leftRooms) {
            for (final Room right : rightRooms) {
                final int dist = manhattanDistance(left.getCenter(), right.getCenter());
                if (dist < bestDist) {
                    bestDist = dist;
                    bestLeft = left;
                }
            }
        }
        return bestLeft;
    }

    private Room findClosestRight(final List<Room> leftRooms, final List<Room> rightRooms) {
        Room bestRight = rightRooms.get(0);
        int bestDist = Integer.MAX_VALUE;
        for (final Room left : leftRooms) {
            for (final Room right : rightRooms) {
                final int dist = manhattanDistance(left.getCenter(), right.getCenter());
                if (dist < bestDist) {
                    bestDist = dist;
                    bestRight = right;
                }
            }
        }
        return bestRight;
    }

    private int manhattanDistance(final Position a, final Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private Hallway createHallway(final Room room1, final Room room2) {
        final Position center1 = room1.getCenter();
        final Position center2 = room2.getCenter();
        final List<Position> path = new ArrayList<>();

        // Create L-shaped corridor
        if (GameRandom.nextBoolean()) {
            // Horizontal
            carveHorizontalLine(path, center1.y(), center1.x(), center2.x());
            if (center1.y() != center2.y()) {
                final int nextY = center2.y() > center1.y() ? center1.y() + 1 : center1.y() - 1;
                carveVerticalLine(path, center2.x(), nextY, center2.y());
            }
        } else {
            // Vertical
            carveVerticalLine(path, center1.x(), center1.y(), center2.y());
            if (center1.x() != center2.x()) {
                final int nextX = center2.x() > center1.x() ? center1.x() + 1 : center1.x() - 1;
                carveHorizontalLine(path, center2.y(), nextX, center2.x());
            }
        }

        // Remove positions inside either room — path should only cover corridor tiles (This fixes the fog of war bug)
        path.removeIf(pos -> room1.contains(pos) || room2.contains(pos));

        return new SimpleHallway(path, List.of(room1, room2));
    }

    private void carveHorizontalLine(final List<Position> path, final int y, final int x1, final int x2) {
        final int startX = Math.min(x1, x2);
        final int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++) {
            path.add(new Position(x, y));
        }
    }

    private void carveVerticalLine(final List<Position> path, final int x, final int y1, final int y2) {
        final int startY = Math.min(y1, y2);
        final int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++) {
            path.add(new Position(x, y));
        }
    }

    private Tile[][] createTileGrid(
            final GenerationConfig config,
            final List<Room> rooms,
            final List<Hallway> hallways) {

        final Tile[][] tiles = new Tile[config.mapHeight()][config.mapWidth()];

        // Fill with walls
        for (int y = 0; y < config.mapHeight(); y++) {
            for (int x = 0; x < config.mapWidth(); x++) {
                tiles[y][x] = Tile.WALL;
            }
        }

        // Carve out rooms
        for (final Room room : rooms) {
            for (int y = room.getTopLeft().y(); y < room.getTopLeft().y() + room.getHeight(); y++) {
                for (int x = room.getTopLeft().x(); x < room.getTopLeft().x() + room.getWidth(); x++) {
                    if (isInBounds(x, y, config)) {
                        tiles[y][x] = Tile.FLOOR;
                    }
                }
            }
        }

        // Carve out hallways
        for (final Hallway hallway : hallways) {
            for (final Position pos : hallway.getPath()) {
                // Only change to corridor if not already floor (to avoid overwriting rooms)
                if (isInBounds(pos.x(), pos.y(), config) && tiles[pos.y()][pos.x()] == Tile.WALL) {
                    tiles[pos.y()][pos.x()] = Tile.CORRIDOR;
                }
            }
        }

        return tiles;
    }

    private boolean isInBounds(final int x, final int y, final GenerationConfig config) {
        return x >= 0 && x < config.mapWidth() && y >= 0 && y < config.mapHeight();
    }
}
