import java.sql.Connection;

public class DoublyLinkedList implements ISaver
{
	private Node head;
	private Node tail;
	private int size;
	
	// CTOR
	public DoublyLinkedList()
	{
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	
	public int size()
	{
		return this.size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}

	public Connection Update(Patients p, int id) 
	{
		return null;
		
	}
	
	public void Add(Patients data)
	{
		if(this.head == null)
		{
			this.head = new Node(data, null, null);
		}
		else
		{
			Node tmp = getNode(this.size);		//get last Node
			
			// Create new node
			Node n = new Node(data, null, null);
			
			// Update the pointer (set the pointer of last Node in dll to new Node)
			tmp.setNext(n);
		}
		
		//increment size (increase size of sll)
		size = size + 1;
	}
	
	public Node getNode(int pos)
	{
		Node n = this.head;
		
		if(pos==1)
		{
			n = this.head;
		}
		else
		{
			for(int i = 1; i < pos; i++)
			{
				n = n.getNext();
			}
		}
		return n;
	}
	
	
	public Node getPreviousNode(int pos)
	{
		Node n = this.tail;
		
		if(pos==this.size)
		{
			n = this.tail;
		}
		else
		{
			//for(int i = 1; i < pos; i++)
			for(int i = pos; i > 1 ; i--)
			{
				n = n.getPrevious();
			}
		}
		return n;
	}
	
	
	public String toString()
	{
		String tmp = " ";
		Node n = this.head;
		Patients p = null;
		for(int i=1; i < this.size; i++) 
		{
			if(i == 1)
			{
				p = (Patients)n.getData();
				tmp = tmp + p.GetFirstname() + ", ";	
			}
			else
			{
				n = n.getNext();
				p = (Patients)n.getData();
				tmp = tmp + p.GetFirstname() + ", ";
			}
		}
		return tmp;
	}
	
	public Patients prinBackwards()
	{
		String tmp = " ";
		Node n = this.tail;
		Patients p = null;
		for(int i=this.size; i > 1; i--) 
		{
			if(i == this.size)
			{
				p = (Patients)n.getData();
				tmp = tmp + n.getData();	
			}
			else
			{
				n = n.getPrevious();
				p = (Patients)n.getData();
				//tmp = tmp + p.GetFirstname() + ", ";
			}
		}
		return p;
	}

	@Override
	public Connection connectToDB() {
		DataBaseConnection dbconn = new DataBaseConnection();
		Connection connection = dbconn.connectToDB();
		
		return connection;
		
	}


	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int GetQueue() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Connection UpdateReception(Patients p, int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Connection UpdateNurse(Patients p, int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Connection UpdateDoctor(Patients p, int id) {
		// TODO Auto-generated method stub
		return null;
	}
}