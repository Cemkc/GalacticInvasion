package myGame.graphics;

public class Screen {
	private int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 6;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
//	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < MAP_SIZE; i++) {
			for(int j = 0; j < MAP_SIZE; j++) {
				if(i == 1 && (j == 2 || j==3)) {
					tiles[j + i * MAP_SIZE] = 0xFFFFFF;
				}
				else {
					tiles[j + i * MAP_SIZE] = 0x000000;
				}
			}
			
			
//			int color = 0x000000;
//			if(i % 2 == 0) {
//				color = random.nextInt(0xFFFFFF);
//			}
//			else {
//				color = 0x505050;
//			}
//			for(int j = 0; j < MAP_SIZE; j++) {
//				if(j % 2 == 0) {
//					tiles[j + i * MAP_SIZE] = 0;
//				}
//				else {
//					tiles[j + i * MAP_SIZE] = color;
//				}
//			}
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render(int xOffset, int yOffset) {			
		for(int y = 0; y < height; y++) {
			int yy = y + yOffset;
			for(int x = 0; x < width; x++) {
				int xx = x + xOffset;
				int tileIndex = ((xx >> 1) & MAP_SIZE_MASK) + ((yy >> 1) & MAP_SIZE_MASK) * MAP_SIZE;
				try {
					pixels[x + y * width] = tiles[tileIndex];
				}catch(IndexOutOfBoundsException e) {
					break;
				}
			}
		}
	}
	
}
