package org.happyuc.webuj.abi.datatypes.generated;

import java.util.List;

import org.happyuc.webuj.abi.datatypes.StaticArray;
import org.happyuc.webuj.abi.datatypes.Type;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.happyuc.webuj.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/happyuc-project/webu.java/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray15<T extends Type> extends StaticArray<T> {
    public StaticArray15(List<T> values) {
        super(15, values);
    }

    @SafeVarargs
    public StaticArray15(T... values) {
        super(15, values);
    }
}