package com.learn.mapping.lazy;

import com.learn.mapping.TestcontainersConfiguration;
import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.basic.entity.Department;
import com.learn.mapping.basic.entity.User;
import com.learn.mapping.bidirectional.BidirectionalMapper;
import com.learn.mapping.service.DepartmentRepository;
import com.learn.mapping.service.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class LazyMapperIntegrationTest {

    @Autowired
    private UserSummaryLazyMapper userSummaryLazyMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    @Test
    void shouldMapUserToUserSummaryDto() {
        User savedUser = createAndSaveUserWithAddresses();
        User managedUser = userRepository.findById(savedUser.getId()).orElseThrow();

        var dto = userSummaryLazyMapper.toSummary(managedUser);

        assertNotNull(dto);
    }

    private User createAndSaveUserWithAddresses() {
        Department department = new Department();
        department.setName("Engineering");
        department.setCode("ENG");
        Department savedDepartment = departmentRepository.save(department);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john-" + System.nanoTime() + "@example.com");
        user.setPassword("secret");
        user.setPhoneNumber("111-222-333");
        user.setStatus(User.UserStatus.ACTIVE);
        user.setDepartment(savedDepartment);

        Address home = new Address();
        home.setStreet("1 Main St");
        home.setCity("Prague");
        home.setState("Prague");
        home.setZipCode("11000");
        home.setCountry("CZ");
        home.setType(Address.AddressType.HOME);
        home.setUser(user);

        Address work = new Address();
        work.setStreet("2 Office Ave");
        work.setCity("Prague");
        work.setState("Prague");
        work.setZipCode("11000");
        work.setCountry("CZ");
        work.setType(Address.AddressType.WORK);
        work.setUser(user);

        Set<Address> addresses = new LinkedHashSet<>();
        addresses.add(home);
        addresses.add(work);
        user.setAddresses(addresses);

        return userRepository.saveAndFlush(user);
    }

}
