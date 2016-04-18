/*
* @Author: S.Zhang
* @Date:   2016-04-12 21:52:32
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-16 21:29:13
*/

    class Board {
        private int x;
        public Board (){
            x = 1;
        }

        public void printX() {
            Board blah = new Board();
            System.out.println(blah.x);
        }
    }

    public class Test {
        public static void main(String[] args) {
            Board a = new Board();
            a.printX(); // no error at compile
            System.out.println(a.x); // compile error
        }
    }