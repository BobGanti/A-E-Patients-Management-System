public class Node 
{
	//data
	private Object data;
	
	//pointer
	private Node next;
	private Node previous;
	
	//CTOR
	public Node(Object data, Node next, Node previous)
	{
		this.data = data;
		this.next = next;	
		this.previous = previous;
	}
	
	//Properties
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}
	
	public Node getNext()
	{
		return next;
	}
	
	public void setNext(Node next) 
	{
		this.next = next;
	}
	
	public Node getPrevious()
	{
		return previous;
	}
	
	public void setPrevious(Node previous) 
	{
		this.previous = previous;
	}
	
}
