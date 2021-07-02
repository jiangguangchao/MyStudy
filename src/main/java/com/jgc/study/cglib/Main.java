package com.jgc.study.cglib;

import com.jgc.study.cglib.bean.Person;
import net.sf.cglib.proxy.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @program: MyStudy
 * @description:
 * @author:
 * @create: 2021-06-30 08:24
 */
public class Main {

    /**
     * Enhancer说明：Enhancer可以用来生成接口或者类的代理对象，通过代理对象可以
     * 对目标对象做一个处理，比如在目标对象执行某些方法之前 或者之后，做入参 参数记录
     * 或者 方法执行返回结果的记录。甚至能修改入参和方法执行返回的结果。
     *
     * 需要给Enhancer指定一个目标类或者接口，比如下面指定目标类，使用
     * enhancer.setSuperclass(Person.class) 指定目标类为Person，生成
     * 代理对象的过程，可以这样理解，先生成一个代理类，这个代理类是继承了Person类（目标类）
     * 然后根据这个代理类生成一个代理对象。所以最终得到的代理对象可以强转为Person
     * 对象（Person p = (Person) enhancer.create();）。
     * 但是打印p.getClass()的返回值，就会发现不是Person类本身的实例对象。
     *
     * 还需要给Enhancer指定一个或者多个Callback，比如下面的MethodInterceptor就是
     * 一个Callback,还有FixedValue，Dispatcher，LazyLoader等
     * 可以这样理解Callback，Callback相当于方法拦截器，在拦截器中我们可以在目标方法执行前
     * 做一些处理，比如记录入参，或者入参检查，比如当前这个测试方法，判断入参中的第一个参数是不是
     * 敏感词，如果是的话，就不调用目标方法。像p.setName("敏感词");这样就不会调用目标的setName方法
     * 所以name属性也不会被设置，依然是默认的null。p.work("敏感词");同样不会执行目标的work方法。
     *
     * 需要注意的是，除了一些特殊的方法，目标对象的每个方法都会走intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
     * 这个方法。这些特殊的方法是指final修饰的方法。根据上面代理生成过程，就很容易理解为什么final不能拦截了。
     *
     * 如果需要指定方法进行拦截，需要设置过滤器 可以参考下面的testFilter测试单元。
     * 如果没有设置过滤器，默认是拦截除final外的所有方法。
     *
     *
     * MethodInterceptor 用来拦截目标类的方法执行，因为没有判定拦截哪些方法，
     * 所以默认会拦截所有方法（构造方法和final方法除外，如果有final方法的话），
     * 所以Person类从父类Object继承的方法也会被拦截，比如toString(),hashCode()，
     * 但是从父类Object继承的final方法 同样不能拦截，比如getClass()
     *
     */
    @Test
    public void testMethodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Person.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("方法[" + method.getName() + "]调用前参数记录，args：" + objects);
                Object result = null;
                if (objects != null && objects.length > 0 && objects[0].toString().equals("敏感词")) {
                    //不执行methodProxy.invokeSuper(o,objects);就相当于没有执行目标方法
                } else {
                    result = methodProxy.invokeSuper(o,objects);
                }
                System.out.println("方法[" + method.getName() + "]调用后结果记录，result：" + result);
                return result;
            }
        });

        Person p = (Person) enhancer.create();
        p.setName("敏感词");
        p.work("敏感词");
        p.toString();
        p.getClass();

    }

    /**
     * FixedValue 也是一个Callback，可以拦截方法并返回固定的值，
     */
    @Test
    public void testFixedValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new FixedValue(){
            @Override
            public Object loadObject() throws Exception {
                return "fixedValue_loadObject";
            }
        });
        enhancer.setSuperclass(Person.class);
        Person p = (Person) enhancer.create();
        System.out.println(p.toString());
        System.out.println(p.hashCode());//报错，因为hashCode方法需要返回一个int类型参数,但是拦截器返回了一个String类型的参数
    }

    /**
     * 拦截指定的方法。CallbackHelper（实现了CallbackFilter接口），可以
     * 当做是一个方法过滤器，可以指定要拦截的方法. 只要实现CallbackHelper 的
     * getCallback(Method method) 方法就可以。该方法需要一个Method对象，
     * 可以指定要拦截的方法，如果方法需要拦截，就返回一个CallBack对象（FixedValue，
     * MethodInterceptor这些都是实现了CallBack接口，还有其他），如果不想拦截，
     * 返回 NoOp.INSTANCE就可以。NoOp.INSTANCE表示不会对目标对象做什么处理。
     *
     */
    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        CallbackHelper helper = new CallbackHelper(Person.class,new Class[0]) {

            @Override
            protected Object getCallback(Method method) {

                //拦截方法返回值类型是int的方法，并改变返回值为-10
                if (method.getReturnType() == int.class) {
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            System.out.println("拦截hashCode方法测试，返回-10");
                            return -10;
                        }
                    };
                }
                //拦截方法参数只有一个string类型的方法
//                if (method.getParameterTypes() == new Class[]{String.class}){
//
//                }
                if (method.getName().equals("work")) {
                    return new MethodInterceptor() {
                        @Override
                        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                            System.out.println("方法[" + method.getName() + "]调用前参数记录，args：" + objects);
                            Object result = methodProxy.invokeSuper(o,objects);
                            System.out.println("方法[" + method.getName() + "]调用后结果记录，result：" + result);
                            return result;
                        }
                    };
                } else {
                    return NoOp.INSTANCE;//其他方法不做任何处理，也就是不会拦截
                }
            }
        };

        enhancer.setCallbackFilter(helper);
        enhancer.setSuperclass(Person.class);
        enhancer.setCallbacks(helper.getCallbacks());

        Person p = (Person) enhancer.create();
        p.setName("小明");
        p.work("编程");
        p.toString();
        p.getClass();
        System.out.println(p.hashCode());


    }
}
