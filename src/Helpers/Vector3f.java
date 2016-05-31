package Helpers;

public class Vector3f {

	float x = 0;
	float y = 0;
	float z = 0;
	
	public Vector3f(float aX, float aY, float aZ)
	{
		this.x = aX;
		this.y = aY;
		this.z = aZ;
	}
	
	public Vector3f(Vector3f vector3f) {
		this.x = vector3f.x();
		this.y = vector3f.y();
		this.z = vector3f.z();
	}
	
	public Vector3f()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public String printVector(){
		return "< " + this.x + ", " + this.y + ", " + this.z + " >";
	}

	public float x()
	{
		return this.x;
	}
	
	public float y()
	{
		return this.y;
	}
	
	public float z()
	{
		return this.z;
	}
	
	public void setX(float aX)
	{
		this.x = aX;
	}
	
	public void setY(float aY)
	{
		this.y = aY;
	}
	
	public void setZ(float aZ)
	{
		this.z = aZ;
	}

	public void scale(float scale) {
		this.x = this.x*scale;
		this.y = this.y*scale;
		this.z = this.z*scale;
	}
	
	public void add(Vector3f inVec){
		this.x = this.x + inVec.x();
		this.y = this.y + inVec.y();
		this.z = this.z + inVec.z();
	}
	
	public float length(){
		return (float)(Math.sqrt((
				Math.pow(x, 2) +
				Math.pow(y, 2) +
				Math.pow(z, 2) )
				));
	}
	
	public void sub(Vector3f inVec) {
		this.x = this.x - inVec.x();
		this.y = this.y - inVec.y();
		this.z = this.z - inVec.z();
	}
	
	public float distanceFrom(Vector3f inVec) {
		float xDist = Math.abs(this.x - inVec.x());
		float yDist = Math.abs(this.y - inVec.y());
		float zDist = Math.abs(this.z - inVec.z());
		return (xDist + yDist + zDist);
	}
	
}
