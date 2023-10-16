package com.graphy.backend.test;

import com.graphy.backend.test.config.TestProfile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(TestProfile.UNIT)
@Disabled
public abstract class MockTest {

}
