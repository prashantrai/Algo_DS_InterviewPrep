//Is the below code written correctly? If yes, what will be the output?
class AA
{
    static void staticMethod()
    {
        System.out.println("Static Method");
    }
}
public class A
{
	A as = new A();
	
	public static void main(String[] args)
    {
        AA a = null;
 
        a.staticMethod(); //NULL
        
        
    }
}