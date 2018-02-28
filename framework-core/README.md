事件机制
====

模块内事件的使用
----
    1、引入spring-event.xml。
    2、同步事件继承SyncEvent，并实现SyncEventHandle。
    3、异步事件集成AsyncEvent，并实现AsyncEventHandle。
    4、自定义EventPublisher，增加对应的事件发布方法，通过ApplicationContext.publishEvent发送事件。
    5、使用时注入EventPublisher，调用相应的发布方法。

使用场景
----
    1、模块内通过同步事件进行代码解耦。
    2、模块内通过异步事件处理比较耗时、没有同步需求的逻辑，如短信发送、邮件发送、日志记录等。
    3、一般情况下异步事件使用较多。