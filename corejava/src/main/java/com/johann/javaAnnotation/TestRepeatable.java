package com.johann.javaAnnotation;

/**
 * @ClassName: TestRepeatable
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
@State(stateValue = "S1")
@State(stateValue = "S2")
/**
 * 在Class中，是以这种形式存在
 * @StateArray({@State(
 *     stateValue = "S1"
 * ), @State(
 *     stateValue = "S2"
 * )})
 */

@AnnotationTest(id = 1,name = "X")
//@AnnotationTest(id = 2,name = "Y")
public class TestRepeatable {
}
