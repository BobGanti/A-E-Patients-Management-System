import java.util.ArrayList;

public class Patients
{
	// Data
	private int id;
	private String firstname;
	private String lastname;
	private int age;
	private int priority;
	public String health;
	public String nurseAssessment;
	private String treatment;
	
	//CTOR
	// Constructors
	public Patients()
	{
			
	}
	
	public Patients(int id)
	{
			this.id = id;
	}
	
	public Patients(String fname, String lname, int age, String health)
	{
		this.firstname = fname;
		this.lastname = lname;
		this.age = age;
		this.health = health;
	}
	
	public Patients(int id, String fname, String lname, int age, String health, int priority, String nurseAssessment) 
	{
	this.id = GetId();
	this.firstname = fname;
	this.lastname = lname;
	this.age = age;
	this.health = health;
	this.priority = priority;
	this. nurseAssessment = nurseAssessment;
	}
	
	public Patients(int id, String fname, String lname, int age, String health, int priority, String nurseAssessment, String treatment) 
	{
	this.id = GetId();
	this.firstname = fname;
	this.lastname = lname;
	this.age = age;
	this.health = health;
	this.priority = priority;
	this. nurseAssessment = nurseAssessment;
	this.treatment = treatment;
	
	}
	
	@Override
	public String toString()
	{
		return String.format("Firstname: %s\t Lastname: %s\t Age: %d\t Health: %s", this.firstname, this.lastname, this.age, this.health);
	}
	
	
	// Properties
	public int GetId()
	{
		return id;
	}
	
	public void SetId(int id)
	{
		this.id = id;
	}
	
	public String GetFirstname()
	{
		return firstname;
	}
	
	public void SetFirstname(String fname)
	{
		this.firstname = fname;
	}
	
	public String GetLastname()
	{
		return lastname;
	}
	
	public void SetLastname(String lname)
	{
		this.lastname = lname;
	}
	
	public int GetAge()
	{
		return age;
	}
	
	public void SetAGe(int age)
	{
		this.age = age;
	}
	
	public int GetPriority()
	{
		return priority;
	}
	
	public boolean SetPriority(int priority)
	{
		if(1 <= priority && 10 >= priority)  // Checks priority limits (1----10. 10 being the highest priority)
		{
			this.priority = priority;
			return true;
		}
		else
		{
			return false;			
		}
	}
	
	public String GetHealth()
	{
		return health;
	}
	
	public void SetHealth(String health)
	{
		this.health = health;
	}
	
	public String GetNurseAssessment()
	{
		return nurseAssessment;
	}
	
	public void SetNurseAssessment(String nurseAssessment)
	{
		this.nurseAssessment = nurseAssessment;
	}
	
	public String GetTreatment()
	{
		return treatment;
	}
	
	public void SetTreatment(String treatment)
	{
		this.treatment = treatment;
	}
	
	public ArrayList<Patients> ListPatients()
	{
		ArrayList<Patients> patientsList = new ArrayList<Patients>();
		
		Patients patient = new Patients(this.GetFirstname(), this.GetLastname(), this.GetAge(), this.GetHealth());
		
		Patients p1 = new Patients("Bob", "Bobga",  70, "Broken nose");	
		Patients p2 = new Patients("Michael", "Jackson", 59, "Dead");		
		Patients p3 = new Patients("Michelle", "Obama", 94, " Very Healthy");
		Patients p4 = new Patients("Leo", "Varadka", 94, "Very fit");
		
		patientsList.add(p1);
		patientsList.add(p2);
		patientsList.add(p3);
		patientsList.add(p4);
		
		return patientsList;
	}

	/*
	public ArrayList<Patients> ListPatients()
	{
		ArrayList<Patients> patientsList = new ArrayList<Patients>();
		
		Patients patient = new Patients(this.GetFirstname(), this.GetLastname(), this.GetAge(), this.GetHealth());
		
		Patients p1 = new Patients("Bob", "Bobga",  70, "Broken nose");	
		Patients p2 = new Patients("Michael", "Jackson", 59, "Dead");		
		Patients p3 = new Patients("Michelle", "Obama", 94, " Very Healthy");
		Patients p4 = new Patients("Leo", "Varadka", 94, "Very fit");
		
		patientsList.add(p1);
		patientsList.add(p2);
		patientsList.add(p3);
		patientsList.add(p4);
		
		return patientsList;
	}*/
	/*
	public void AddPatient(Patients patient)
	{
		String PatientsQuery = "INSERT INTO Patients (Firstname, Lastname, Age, HealthComplaints) VALUES ('"+patient.GetFirstname()+"', "+ "'"+patient.GetLastname()+"', " + "'"+patient.GetAge()+"', " + "'"+patient.GetHealth()+"'"+")";
		DataBaseConnection dbc = new DataBaseConnection();
		dbc.Verify_DBConnection();
		dbc.ExecuteQuery(PatientsQuery);
	}
	*/
}
