import static java.lang.System.out;class Main{public static void main(String[]a){Person p=new Person("Greg",18);out.println("Person name: "+p.getName());out.println("Person age : "+p.getAge());out.println(p.getName()+(p.getAge()<18?" can't vote.":" can vote!"));}}