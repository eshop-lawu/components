package com.lawu.framework.web.event;

/**
 * @author Leach
 * @date 2017/7/2
 */
public interface UserVisitEventPublish {


    void publishUserVisitEvent(String userNum,Long userId);
}
