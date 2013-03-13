package com.mtao.challengenfactorial;

public class Vector2 {
	Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	float x;
	float y;
	Vector2 add(Vector2 rhs) {
		return new Vector2(x+rhs.x,y+rhs.y);
	}
	Vector2 subtract(Vector2 rhs) {
		return new Vector2(x-rhs.x, x-rhs.y);
	}
}
