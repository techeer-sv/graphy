package com.graphy.backend.test;

import com.graphy.backend.test.config.TestProfile;
import org.junit.Ignore;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(TestProfile.TEST)
@Ignore
public class MockTest {

}
