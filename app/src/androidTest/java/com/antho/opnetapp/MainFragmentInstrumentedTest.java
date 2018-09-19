package com.antho.opnetapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.antho.opnetapp.models.GithubUser;
import com.antho.opnetapp.models.GithubUserInfo;
import com.antho.opnetapp.streams.GithubStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentInstrumentedTest {

    @Test
    public void fetchUserFollowingTest() throws Exception {
        Observable<List<GithubUser>> observableUsers = GithubStreams.streamFetchUserFollowing("JakeWharton");
        TestObserver<List<GithubUser>> testObserver = new TestObserver<>();

        observableUsers.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<GithubUser> usersFetched = testObserver.values().get(0);
        assertThat("Jake Wharton follows only 12 users.", usersFetched.size() == 12);
    }

    @Test
    public void fetchUserInfosTest() throws Exception {
        Observable<GithubUserInfo> observableUser = GithubStreams.streamFetchUserInfos("JakeWharton");
        TestObserver<GithubUserInfo> testObserver = new TestObserver<>();

        observableUser.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        GithubUserInfo userInfo = testObserver.values().get(0);
        assertThat("Jake Wharton Github's ID is 66577.", userInfo.getId() == 66577);
    }
}
