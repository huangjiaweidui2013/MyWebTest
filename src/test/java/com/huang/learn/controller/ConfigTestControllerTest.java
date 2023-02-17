package com.huang.learn.controller;

import com.huang.learn.common.AjaxResult;
import com.huang.learn.config.MyPropertiesTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Map;

import static org.mockito.Mockito.when;

public class ConfigTestControllerTest {
    @Mock
    MyPropertiesTest myPropertiesTest;
    @Mock
    Map<String, String> strMap;
    @Mock
    Map<Integer, Integer> intMap;
    @Mock
    Logger log;
    @InjectMocks
    ConfigTestController configTestController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetConfig() throws Exception {
        when(myPropertiesTest.getObjMap()).thenReturn(Map.of("String", Integer.valueOf(0)));
        when(myPropertiesTest.getWorkerId()).thenReturn(Long.valueOf(1));
        when(myPropertiesTest.toString()).thenReturn("toStringResponse");

        AjaxResult result = configTestController.getConfig();
        Assert.assertEquals(new AjaxResult(0, "msg", "data"), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme