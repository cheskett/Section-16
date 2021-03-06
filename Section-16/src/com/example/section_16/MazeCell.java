package com.example.section_16;

import java.io.Serializable;


import android.graphics.Point;

public class MazeCell implements Serializable{
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
	
	
	//public static MazeCell a[][] = null;
	
	public static Point playerPos = null;
	
	//Constructor
	public MazeCell(int x, int y, MazeCell parent){
		this.wall = true;
		this.x=x;
		this.y=y;
		this.visible = false;
		this.parent = parent;	
	}
	
	//for savestate
	public MazeCell(int type){
		if(type == 0){
			start = true;
		}else if(type == 1){
			end = true;
		}else if(type == 2){
			wall = true;
		}else if(type == 3){
			obstacle = true;
		}else if(type == 4){
			player = true;
		}
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
	
	public boolean isEnd(){
		return this.end;
	}
	
	public boolean isPlayer(){
		return this.player;
	}
	
	public Point coordinates(MazeCell cell){
		Point point = new Point(this.x, this.y);
		return point;
	}
	
	public static boolean checkBorder(MazeCell[][] maze)
	{
		for(int i=0; i < maze.length; i++)
		{
			if(!maze[i][0].isWall() || !maze[i][maze.length - 1].isWall() || !maze[0][i].isWall() || !maze[maze.length -1][i].isWall() )
			{
				return false;
			}
		}
		return true;
	}
}
