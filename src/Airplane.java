
public class Airplane {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

/*
https://redquark.org/leetcode/0010-regular-expression-matching/ 
  
Leetcode 10; 
Problem:
- Given an input `s` and a regex pattern `p`, write a function `match(s, p)`
  that returns whether s matches p completely.

- `p` will only contain lowercase letters, `.` (matches any single character),
  and `*` (matches zero or more of the previous character)

- You only need to support valid patterns, e.g. you'll never see `a**`


s="aabb"
p="a*b*"

s=aaa bb"
p=a*a  b*

s=bb 
p=a* b*b => true
p=a* b*bb => true

p=a* bbb* = true

p=a* b*bbb = false

expected output: true

"a*b*" => a\\w*b\\w*
s.mathces(p)

*/
