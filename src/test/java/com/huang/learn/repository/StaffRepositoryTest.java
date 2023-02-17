package com.huang.learn.repository;

import com.huang.learn.domain.Staff;
import com.huang.learn.util.MongoBusinessUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DeptRepository deptRepository;

    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        Staff staff = MongoBusinessUtil.generateStaff(1L);
        staffRepository.save(staff);

        //被引用的文档需要单独插入
        deptRepository.save(staff.getDept());

    }

    /**
     * 测试新增
     */
    @Test
    public void testSave2() {
        Staff staff = MongoBusinessUtil.generateStaff(null);
        staffRepository.save(staff);

        //被引用的文档需要单独插入
        deptRepository.save(staff.getDept());

    }

    @Test
    public void testQuery() {
        Long id = 1L;
        staffRepository.findById(id).ifPresent(staff -> {
            log.info("[staff] = {} ", staff);
        });
    }
}