package business;

public class Student extends Personne{
	// attributes
	private String cne_student;
	private float note_finale;
	private int abscence_hours;
	private String classe_student;
	
	// constructors 
	public Student(int id,String username,String password,String name, String last_name, String address, String sex, int age, String cne_student) {
		super(id,username,password,name, last_name, address, sex, age);
		this.cne_student = cne_student;
	}
	

	public Student(int id,String username,String password,String name, String last_name, String address, String sex, int age, String cne_student,int abscence_hours) {
		super(id,username,password,name, last_name, address, sex, age);
		this.cne_student = cne_student;
		this.abscence_hours=abscence_hours;
	}
	

	public Student(String username,String password,String name, String last_name, String address, String sex, int age, String cne_student,int abscence_hours) {
		super(username,password,name, last_name, address, sex, age);
		this.cne_student = cne_student;
		this.abscence_hours=abscence_hours;
	}
	
	// getter & setter 
	public String getId_student() {
		return cne_student;
	}
	

	public float getNote_finale() {
		return note_finale;
	}
	public void setNote_finale(float note_finale) {
		this.note_finale = note_finale;
	}
	public int getAbscence_hours() {
		return abscence_hours;
	}
	public void setAbscence_hours(int abscence_hours) {
		this.abscence_hours = abscence_hours;
	}

	

	public String getCne_student() {
		return cne_student;
	}



	public void setCne_student(String cne_student) {
		this.cne_student = cne_student;
	}



	public String getClasse_student() {
		return classe_student;
	}



	public void setClasse_student(String classe_student) {
		this.classe_student = classe_student;
	}



	@Override
	public String toString() {
		return super.toString()+" | CNE : " + cne_student + " | Abscence : "
				+ abscence_hours + " heurs ";
	}





	
	
	
}
