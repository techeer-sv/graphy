package com.graphy.backend.domain.job.service;

import com.graphy.backend.domain.job.domain.Job;
import com.graphy.backend.domain.job.dto.JobDto;
import com.graphy.backend.domain.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
@Transactional
@RequiredArgsConstructor
public class JobService {
    @Value("${job.key}")
    String accessKey;
    private final JobRepository jobRepository;


    public void save() {
        StringBuffer response = new StringBuffer();

        try {
            String text = URLEncoder.encode("", "UTF-8");
            String apiURL = "https://oapi.saramin.co.kr/job-search?access-key=" + accessKey + "&bbs_gb=0&job_type=&edu_lv=&job_mid_cd=2";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        saveJobInfo(response.toString());
    }

    private void saveJobInfo(String response) {

        JSONObject jsonObject = new JSONObject(response);

        JSONArray jobsArray = jsonObject.getJSONObject("jobs").getJSONArray("job");

        for (int i = 0; i < jobsArray.length(); i++) {
            JSONObject jobObject = jobsArray.getJSONObject(i);

            // 공고 ID
            Long jobId = jobObject.getLong("id");
//            if (jobRepository.findById(jobId) != null) break;

            // 회사 이름
            String companyName = jobObject.getJSONObject("company")
                    .getJSONObject("detail")
                    .getString("name");

            // 공고 제목
            String jobTitle = jobObject.getJSONObject("position")
                    .getString("title");

            // URL
            String companyInfoURL = jobObject.getJSONObject("company")
                    .getJSONObject("detail")
                    .getString("href");

            // 만료일
            long expirationTimestampLong = jobObject.getLong("expiration-timestamp");
            LocalDateTime expirationTimestamp = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(expirationTimestampLong),
                    ZoneId.systemDefault());

            JobDto.CreateJobInfoRequest dto = new JobDto.CreateJobInfoRequest(jobId, companyName, jobTitle, companyInfoURL, expirationTimestamp);
            jobRepository.save(dto.toEntity());
        }
    }


    public void deleteExpiredJobs() {
        jobRepository.deleteAllExpiredSince(LocalDateTime.now());
    }
}
