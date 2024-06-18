package com.example.viewModel;

import com.example.database.Mood;
import com.example.dto.ImageDTO;
import com.example.dto.TagDTO;
import com.example.mapper.ImageMapper;
import com.example.mapper.TagMapper;
import com.example.service.ImageService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static com.example.converter.StringConverter.convertSetToStringSpace;
import static com.example.converter.TagParser.parseTags;
import static com.example.converter.ThumbnailCompressor.compressImage;

@Getter
@Setter
@Component
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EditViewModel {
    @WireVariable
    private ImageService imageService;
    @WireVariable
    public ImageMapper imageMapper;
    @WireVariable
    public TagMapper tagMapper;

    private ImageDTO imageDto;

    private String imageName;
    private String name;
    private String description;
    private ListModelList<Mood> dropdownModel;
    private Mood mood;
    private String tags;
    private byte[] content;
    private byte[] thumbnail;

    @Init
    public void init(@QueryParam("id") Long id) throws IOException, NotFoundException {
        imageDto = imageService.findImageById(id);
        tags = convertSetToStringSpace(imageDto.getTags());
        name = imageDto.getName();
        description = imageDto.getDescription();
        mood = imageDto.getMood();
        content = imageDto.getContent();
        thumbnail = imageDto.getThumbnail();
        dropdownModel = new ListModelList<>(Mood.values());
    }

    @Command
    @NotifyChange({"imageName", "imageDto","thumbnail"})
    public void fetchFile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) throws IOException {
        Media media = event.getMedia();
        content = media.getByteData();

        imageName = media.getName();
        thumbnail = compressImage(content, 250);
    }

    @Command
    @NotifyChange({"content", "name", "description", "mood", "tags"})
    public void saveEditedImageToDatabase() throws IOException, NotFoundException {

        if (content == null) {
            Clients.showNotification("You need to upload an image.",
                    "warning", null, "middle_center", 0);
        } else if (name == null) {
            Clients.showNotification("You need to enter a name.",
                    "warning", null, "middle_center", 0);
        } else if (description == null) {
            Clients.showNotification("You need to enter a description.",
                    "warning", null, "middle_center", 0);
        } else if (mood == null) {
            Clients.showNotification("You need to select a mood.",
                    "warning", null, "middle_center", 0);
        } else if (tags == null){
            Clients.showNotification("You need to enter tags.",
                    "warning", null, "middle_center", 0);
        } else {

            imageDto.setContent(content);
            imageDto.setThumbnail(thumbnail);
            imageDto.setName(name);
            imageDto.setDescription(description);
            imageDto.setMood(mood);

            Set<TagDTO> tagSet = parseTags(tags);
            imageDto.setTags(tagSet);
            imageService.storeOrUpdateImage(imageDto);
            Messagebox.show("Image edit was successful. Press OK to continue.", "Success", Messagebox.OK, Messagebox.INFORMATION,
                    event -> {
                        if (Objects.equals(event.getName(), Messagebox.ON_OK)) {
                            Executions.sendRedirect("index.zul");
                        }
                    });
        }
    }
}
