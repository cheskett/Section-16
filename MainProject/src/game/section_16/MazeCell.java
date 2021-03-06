package game.section_16;

import android.graphics.Point;

public class MazeCell {
	//Coordinates
	int x, y;
	
	//Cell qualities
	boolean start;
	boolean end;
	boolean wall;
	boolean visible;
	boolean obstacle;
	boolean player;
	
	//Parent cell for computing opposite
	MazeCell parent;
	
	//Contructor
	public MazeCell(int x, int y, MazeCell parent){
		this.wall = true;
		this.x=x;
		this.y=y;
		this.parent = parent;		
	}
	
	//Compute cell located on opposite side of parent
	public MazeCell oppositeCell(){
		int xDif = this.x - parent.x;
		int yDif = this.y - parent.y;
		
		if(xDif != 0){
			return new MazeCell(x+xDif, y, this);
		}
		if(yDif != 0){
			return new MazeCell(x, y+yDif, this);
		}		
		return null;		
	}
	
	public boolean isWall(){
		return this.wall;
	}

	public boolean isVisible(){
		return this.visible;
	}
	
	public boolean hasObstacle(){
		return this.obstacle;
	}
	
	public boolean isStart(){
		return this.start;
	}
	
	public boolean isEnd(MazeCell cell){
		return this.end;
	}
	
	public Point coordinates(MazeCell cell){
		Point point = new Point(this.x, this.y);
		return point;
	}
}
