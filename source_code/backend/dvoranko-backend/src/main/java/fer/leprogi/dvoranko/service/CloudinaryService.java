package fer.leprogi.dvoranko.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fer.leprogi.dvoranko.utils.FolderName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud.name}") String cloudName,
            @Value("${cloudinary.api.key}") String apiKey,
            @Value("${cloudinary.api.secret}") String apiSecret){

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true));
    }

    public String upload(MultipartFile file, Long dvoranaId, int id, FolderName folder) throws IOException{

//        ArrayList<String> urls = new ArrayList<>();
//        int i = 0;
//        for (MultipartFile file : files) {}
        Map options = ObjectUtils.asMap(
                "folder", folder.toString() + "/" + dvoranaId,
                "public_id", "img_" + id,
                "overwrite", true
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

        return uploadResult.get("secure_url").toString();
    }
}
