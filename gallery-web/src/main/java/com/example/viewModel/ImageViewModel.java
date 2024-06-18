package com.example.viewModel;

import com.example.dto.ImageDTO;
import com.example.service.ImageService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.lang.Objects;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.io.IOException;

import static com.example.converter.StringConverter.convertSetToString;

@Getter
@Setter
@Component
@VariableResolver(DelegatingVariableResolver.class)
public class ImageViewModel {

    @WireVariable
    private ImageService imageService;

    private ImageDTO imageDto;
    private byte[] content;
    private String tags;

    @Init
    public void init(@QueryParam("id") Long id) throws IOException, NotFoundException {
        imageDto = imageService.findImageById(id);
        tags = convertSetToString(imageDto.getTags());
    }

    @Command
    public void deleteImage() {
        Messagebox.show("Are you sure you want to delete this image?", "Confirmation",
                Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
                event -> {
                    if (Objects.equals(event.getData(), Messagebox.YES)) {
                        imageService.deleteImage(imageDto.getId());
                        Clients.evalJavaScript("alert('Image successfully deleted');");
                        Executions.sendRedirect("/index.zul");
                    }
                });
    }

    @Command
    public void goToEditImage(@BindingParam("id") Long id) {
        Executions.sendRedirect("/edit.zul?id=" + id);
    }


}
