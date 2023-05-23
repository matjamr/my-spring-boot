package efs.task.todoapp.init.commons.http;

public class HttpUtils {

    private HttpUtils() {}

    public static String handlePathVar(String path) {
        if(path.matches(".+\\{(.*)")) {
            return path.split("\\{")[0];
        }

        return path;
    }


    public static String handlePathVarUri(String path) {
        String[] tab = path.split("\\/");

        String ret = tab[0].concat("/");

        for(int i=1; i < tab.length - 1; i++) {
            ret = ret.concat(tab[i]).concat("/");
        }

        return ret;
    }

    public static void main(String[] args) {
        System.out.println(handlePathVarUri("http/1231231/nigger"));
        System.out.println(handlePathVarUri("http"));
    }


}
