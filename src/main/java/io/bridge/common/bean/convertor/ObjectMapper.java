package io.bridge.common.bean.convertor;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public enum ObjectMapper {
    INSTANCE;

    private MapperFacade mapperFacade;

    private ObjectMapper() {
        mapperFacade = BaseMapper.MAPPER_FACTORY.getMapperFacade();
    }

    /**
     * 对象转换
     * @param obj
     * @param tclass
     * @param <T>
     * @return
     */
    public <T> T map(Object obj, Class tclass) {
        return (T) this.mapperFacade.map(obj, tclass);
    }

    /**
     * 对象转换
     * @param objList
     * @param tclass
     * @param <T>
     * @return
     */
    public <T> List<T> mapList(List<?> objList, Class tclass) {
        if(CollectionUtils.isEmpty(objList))
            return null;
        List<T> result = new ArrayList<>();
        for(Object obj : objList){
            result.add((T) this.mapperFacade.map(obj, tclass));
        }
        return result;
    }

    /**
     * 注册类复杂属性的映射
     * @param classa
     * @param classb
     * @param attributeMap
     */
    public synchronized void register(Class classa, Class classb, Map<String,String> attributeMap){
        ClassMapBuilder classMapBuilder = BaseMapper.MAPPER_FACTORY.classMap(classa,classb);
        Iterator<Map.Entry<String,String>> iterator = attributeMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            classMapBuilder.field(entry.getKey(), entry.getValue());
        }
        classMapBuilder.byDefault().register();
        mapperFacade = BaseMapper.MAPPER_FACTORY.getMapperFacade();
    }
//
//    例子：
//    @Data
//    public static class A {
//        private Integer a;
//        private C c;
//    }
//    @Data
//    public static class B {
//        private Integer b;
//        private C c;
//    }
//    @Data
//    public static class C {
//        private Integer c;
//    }
//    public static void main(String[] args){
//        A a = new A();
//        C c = new C();
//        a.setA(9999);
//        a.setC(c);
//        c.setC(10000);
//
//        Map<String,String> attrMap = new HashMap<>();
//        attrMap.put("a","b");
//        DefaultObjectMapper.INSTANCE.register(A.class,B.class,attrMap);
//
//        B b = DefaultObjectMapper.INSTANCE.map(a,B.class);
//        System.out.println(b.getB());
//        System.out.println(b.getC().getC());
//    }
}
