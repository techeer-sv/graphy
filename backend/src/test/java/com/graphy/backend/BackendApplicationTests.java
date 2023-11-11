package com.graphy.backend;

import com.graphy.backend.test.config.TestProfile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(TestProfile.TEST)
class BackendApplicationTests {

}
