package corp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeErasureVsLambda {

    interface JustAGeneric<T,R> {
        R action(T t);
    }

    static void reflectionResolveParams(JustAGeneric<?,?> r) {
        System.out.println("Checking " + r.getClass());

        var intfs = r.getClass().getGenericInterfaces();
        if (intfs.length == 0) {
            System.out.println("  Type implements no generic interfaces!");
        }

        for (Type type : intfs) {
            System.out.println("  implements " + type);
            if (type instanceof ParameterizedType) {
                var pType = (ParameterizedType) type;
                if (pType.getRawType().equals(JustAGeneric.class)) {
                    System.out.print("  Resolved type parameters of JustAGeneric:");
                    for (Type arg : pType.getActualTypeArguments()) {
                        System.out.print("    " + arg + ", ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {
        //noinspection Convert2MethodRef
        JustAGeneric<Integer, String> explicitLambda = x -> x.toString();
        reflectionResolveParams(explicitLambda);

        JustAGeneric<Integer, String> explicitRef = Object::toString;
        reflectionResolveParams(explicitRef);

        //noinspection Convert2Lambda,Anonymous2MethodRef
        JustAGeneric<Integer, String> explicitObj = new JustAGeneric<>() {
            @Override
            public String action(Integer integer) {
                return integer.toString();
            }
        };
        reflectionResolveParams(explicitObj);


    }

}
