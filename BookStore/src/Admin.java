
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class Admin extends Book_Main{
	Connection con=null;
	void getConection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "Meha@2904");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	static Scanner sc=new Scanner(System.in);
	public void ahome() throws Exception{
		System.out.print("Admin Name: ");
		String as=sc.nextLine();
		System.out.print("Admin Password: ");
		String ap=sc.nextLine();
		System.out.println();
		
		if(as.equals("Meha") && ap.equals("2904")) {
			admin();
		}
		else {
			System.out.println("Invalid login");
			ahome();
			displaybooks1();
		}
	}
	/*public void aoption{
	 * System.out.println("1.Login\n2.SignUp");
		int n=sc.nextInt();
		if(n==1)
			adminHome();
		else
			signup();
		}
		public void adminHome{
		System.out.println("Admin username: ");
		String admin=sc.nextLine();
		System.out.println("Admin password: ");
		String pass=sc.nextLine();
		if(acheck(admin,pass)){
		System.out.println("Login successfull");
		amin();
		}
		else{
		System.out.println("Invalid login");
		ahome();
		displaybooks1();  
		}
	*/
	public void admin() throws Exception {
		System.out.println();
		System.out.println("Select an Option");
		System.out.print("[A].Add books\n[B].Delete books\n[C].Update details\n[D].Sold books\n[E].View book\n[F].Exit\n");
		char ch=sc.next().charAt(0);
		sc.nextLine();
		if(ch=='a' || ch=='A') {
			System.out.println("Add new Book");
			aadd();
			admin();
		}
		else if (ch=='b' || ch=='B'){
			System.out.println("Delete Book");
			deletebook();
			admin();
		}
		else if(ch=='c'||ch=='C') {
			System.out.println("Update book details");
			updatebook();
			admin();
		}
		else if(ch=='d'||ch=='D') {
			soldbooks();
			admin();
		}
		else if(ch=='e'|| ch=='E') {
			displaybooks1();
			admin();
		}
		else if(ch=='f'||ch=='F') {
			home();
		}
	}
	public void aadd()throws Exception {
		getConection();
		System.out.print("Enter Book name: ");
		String abn=sc.nextLine();
		System.out.print("Enter Author name: ");
		String aan=sc.nextLine();
		System.out.print("Enter book edition: ");
		int ae=sc.nextInt();
		System.out.print("Enter book price: ");
		int ap=sc.nextInt();
		sc.nextLine();
		System.out.println();
		ainsert(abn, aan, ae, ap);
		System.out.println("Data has entered successfully!!!");
	}
	public void updatebook()throws Exception {
		Scanner sc=new Scanner(System.in);
		System.out.print("1.Bookname\n2.Authorname\n3.Edition\n4.Price");
		int n=sc.nextInt();
		sc.nextLine();
		if(n==1) {
			System.out.print("Bookname: ");
			String strb=sc.nextLine();
			System.out.print("New Bookname: ");
			String strb1=sc.nextLine();
			getConection();
			String query="UPDATE book set book_name='"+strb1+"' where book_name='"+strb+"'";
		  	Statement sat=(Statement)con.createStatement();
		  	int resultset=sat.executeUpdate(query);
		  	System.out.println("New Book Name updated");
		}
		else if(n==2) {
			System.out.print("Enter book name: ");
			String bn2=sc.nextLine();
			System.out.print("New Authorname: ");
			String stra1=sc.nextLine();
			getConection();
			String query="UPDATE book set author_name='"+stra1+"' where book_name='"+bn2+"'";
		  	Statement sat=(Statement)con.createStatement();
		  	int resultset=sat.executeUpdate(query);
		  	System.out.println("New Author Name updated");
		}
		else if(n==3) {
			System.out.print("Enter book name: ");
			String bn=sc.nextLine();
			System.out.print("New Edition: ");
			int e1=sc.nextInt();
			getConection();
			String query="UPDATE book set edition='"+e1+"' where book_name='"+bn+"'";
		  	Statement sat=(Statement)con.createStatement();
		  	int resultset=sat.executeUpdate(query);
		  	System.out.print("New Edition is updated");
		}
		else if(n==4){
			System.out.print("Enter book name: ");
			String bn1=sc.nextLine();
			System.out.print("New Price: ");
			int p1=sc.nextInt();
			getConection();
			String query="UPDATE book set price='"+p1+"' where book_name='"+bn1+"'";
		  	Statement sat=(Statement)con.createStatement();
		  	int resultset=sat.executeUpdate(query);
		  	System.out.println();
		  	System.out.print("New Price updated");
		}
	}
	public void soldbooks() throws SQLException {
		getConection();
		String query="select book_name,sum(quantity),purchase_time from purchase order by purchase_time DESC";
		Statement sat=con.createStatement();
		ResultSet res=sat.executeQuery(query);
		System.out.printf("%-25s | %-18s%n | %-15s%n","book_name","quantity","purchase_time");
		while(res.next()) {
			System.out.printf("%-25s | %-18s%n",res.getString(1),res.getInt(2),res.getTimestamp("purchase_time"));
		}
	}
	private void displaybooks1() throws SQLException {
		getConection();
		String query="select * from book";
		Statement sat=con.createStatement();
		ResultSet res=sat.executeQuery(query);
		//new printTable().printResult("book");
		System.out.println("-".repeat(67));
		System.out.printf("%-25s | %-18s | %-9s | %-7s%n", "book_name", "author_name", "edition", "price ");
		System.out.println("-".repeat(67));
		while (res.next()) {
		    System.out.printf("%-25s | %-18s | %-9d | %-7d%n", res.getString(1), res.getString(2), res.getInt(3), res.getInt(4));
		}
		System.out.println("-".repeat(67));
		/*System.out.println("        book_name         |    author_name    |  edition  |  price  ");
		while(res.next())
			System.out.println(res.getString(1)+" "+res.getString(2)+" "+res.getInt(3)+" "+res.getInt(4));*/
	}
	
	public void ainsert(String abn,String aan,int ae,int ap) throws Exception{
		getConection();
		String query="INSERT INTO book(book_name, author_name, edition, price)values('"+abn+"','"+aan+"','"+ae+"','"+ap+"')";
	  	Statement sat=(Statement)con.createStatement();
	  	int resultset=sat.executeUpdate(query);
	}
	static int count=0;
	public void deletebook()throws Exception{ 
		System.out.print("Book name: ");
		String dbn=sc.nextLine();
		System.out.println();
		adelete(dbn);
		if(count==1) {
			System.out.println("Data has deleted successfully!!!");
		}
		else
			System.out.println("Invalid bookname");
	}
	public void adelete(String dbn) throws Exception{
		getConection();
		String query="DELETE from book where book_name='"+dbn+"'";
	  	Statement sat=(Statement)con.createStatement();
	  	int resultset=sat.executeUpdate(query);
	  	count=1;
	}
}
