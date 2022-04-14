package interview;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SoFi {

	public static void main(String[] args) {
		BigDecimal value = new BigDecimal(5);
		BigDecimal value1 = new BigDecimal(5);
		Money m = new Money(value);
		m.setValue("6");
		System.out.println(m.getDoll());
		System.out.println(m.getCent());
		System.out.println(m.mult(value1));
		
		
	}
	
	
	

}

class Money {
	BigDecimal value;
	public Money(){}
	public Money(BigDecimal value){
		this.value = value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
		
	}
	public void setValue(String svalue) {
		this.value = new BigDecimal(svalue);
		
	}
	
	public BigDecimal getDoll() {
		return value.setScale(0, RoundingMode.FLOOR);
	}
	
	public BigDecimal getCent() {
		return value.subtract(getDoll());
	}
	
	public Money mult(BigDecimal v) {
		return new Money(v.multiply(v).setScale(2, RoundingMode.HALF_UP));
		
	}
	@Override
	public String toString() {
		return "Money [value=" + value + "]";
	}
	
	
	
}
