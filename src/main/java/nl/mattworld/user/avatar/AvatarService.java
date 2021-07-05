package nl.mattworld.user.avatar;

import nl.mattworld.exceptions.AvatarUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AvatarService {

    @Value("${avatar.upload-dir}")
    public String uploadDir;

    public String save(String fileId, MultipartFile file) {
        try {
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length - 1];
            String fileName = fileId + "." + extension;

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path copyLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/{uploadDir}/{filename}")
                    .buildAndExpand(uploadDir, fileName).toUri();

            System.out.println("Avatar uploaded to path: " + copyLocation + " at url: " + location);

            return location.toString();
        } catch (Exception ex) {
            throw new AvatarUploadException("Error during upload of avatar", ex);
        }
    }
}
