package org.getfit.cloud;

import com.google.gson.Gson;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CloudImageExtractor {
    private final Map<String, List<String[]>> usersCloudData;

    private static final String PICTURE_FOLDER_ID = "2348176201";

    private static final String QUERY_PATH_SEPARATOR = "?";

    private static final String QUERY_PARAMETER_SEPARATOR = "&";

    private static final String FOLDER_ID_PARAMETER = "folderid=";

    private static final String FILE_ID_PARAMETER = "fileid=";

    private static final String AUTH_PARAMETER = "auth=";

    private static final String CODE_PARAMETER = "code=";

    private static final String LIST_FOLDER_URL
            = "https://api.pcloud.com/listfolder";

    private static final String LIST_FILE_URL
            = "https://api.pcloud.com/getfilepublink";

    private static final String DOWNLOAD_FILE_URL
            = "https://api.pcloud.com/getpublinkdownload";

    private final HttpRequestExecutor httpRequestExecutor;

    private final CloudAuthorizationService cloudAuthorizationService;

    private final Gson gson;

    public CloudImageExtractor(HttpRequestExecutor httpRequestExecutor, CloudAuthorizationService cloudAuthorizationService, Gson gson) {
        this.httpRequestExecutor = httpRequestExecutor;
        this.cloudAuthorizationService = cloudAuthorizationService;
        this.gson = gson;
        this.usersCloudData = new HashMap<>();
    }

    @CachePut("images")
    public List<String[]> getAllImages(String name) throws IOException {
        ArrayList<Map<String, Object>> filesData = getFiles();

        // check if there are new images(userImages - > new userImg = new USER),
        // if there are, we are doing foreach and extract the useful data.
        if (usersCloudData.get(name) != null && filesData.size() == usersCloudData.get(name).size()) {
            return usersCloudData.get(name);
        }

        List<String[]> resultImages = new ArrayList<>();


        for (Map<String, Object> singleFileData : filesData) {
            String fileId = singleFileData.get("id").toString().substring(1);

            String fileListJsonResult = this.httpRequestExecutor.executeGetRequest(
                    LIST_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + FILE_ID_PARAMETER
                            + fileId
                            + QUERY_PARAMETER_SEPARATOR
                            + AUTH_PARAMETER
                            + this.cloudAuthorizationService.getAccessToken()
            ).body()
                    .string();

            String fileCode = gson.fromJson(fileListJsonResult, Map.class).get("code").toString();

            String fileDownloadJsonResult = this.httpRequestExecutor.executeGetRequest(
                    DOWNLOAD_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + CODE_PARAMETER
                            + fileCode
            ).body()
                    .string();

            Map<String, Object> fileDownloadData = gson.fromJson(fileDownloadJsonResult, Map.class);

            String filePath = fileDownloadData.get("path").toString();
            String host = ((ArrayList<String>) fileDownloadData.get("hosts")).get(0);

            String[] imageData = new String[2];
            imageData[0] = singleFileData.get("name").toString();
            imageData[1] = "https://" + host + filePath;

            resultImages.add(imageData);
        }


        usersCloudData.put(name, resultImages);
        return resultImages;
    }

    public ArrayList<Map<String, Object>> getFiles() throws IOException {

        Gson gson = new Gson();
        String accessToken = this.cloudAuthorizationService.getAccessToken();

        String folderJsonResult = this.httpRequestExecutor.executeGetRequest(
                LIST_FOLDER_URL
                        + QUERY_PATH_SEPARATOR
                        + FOLDER_ID_PARAMETER
                        + PICTURE_FOLDER_ID
                        + QUERY_PARAMETER_SEPARATOR
                        + AUTH_PARAMETER
                        + accessToken
        ).body()
                .string();

        Map<String, Object> folderData = gson.fromJson(folderJsonResult, Map.class);

        return (ArrayList<Map<String, Object>>) ((Map<String, Object>) folderData.get("metadata")).get("contents");


    }

//    public List<String[]> get

}
