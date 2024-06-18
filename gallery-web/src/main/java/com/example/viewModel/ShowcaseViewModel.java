package com.example.viewModel;

import com.example.dto.ShowcaseDTO;
import com.example.service.ImageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class ShowcaseViewModel {

    @WireVariable
    private ImageService imageService;

    private Page<ShowcaseDTO> pagedList;
    private int activePage = 0;

    @Init
    public void init() {
        loadPage();
    }

    private void loadPage() {

        pagedList = imageService.showcasePage(activePage, 8);
    }

    @Command
    @NotifyChange("pagedList")
    public void goToPage(@BindingParam("page") int page) {
        activePage = page;
        loadPage();
    }

    @Command
    @NotifyChange("pagedList")
    public void nextPage() {
        if (activePage < pagedList.getTotalPages() - 1) {
            activePage++;
            loadPage();
        }
    }

    @Command
    @NotifyChange("pagedList")
    public void previousPage() {
        System.out.println("clicked");
        if (activePage > 0) {
            activePage--;
            loadPage();
        }
    }

    @Command
    public void redirectToImagePage(@BindingParam("id")Long id) {
        Executions.sendRedirect("/image.zul?id=" + id);
    }

}