package com.example.viewModel;

import com.example.database.Mood;
import com.example.dto.ImageDTO;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@VariableResolver(DelegatingVariableResolver.class)
public class SearchViewModel {

    @WireVariable
    private ImageService imageService;

    private Page<ImageDTO> pagedList;
    private ListModelList<Mood> dropdownModel;
    private Mood mood;
    private int activePage = 0;
    private String keyword;
    public String nothing;

    @Init
    public void init() {
        dropdownModel = new ListModelList<>(Mood.values());
    }
    @Command
    @NotifyChange("pagedList")
    public void loadPage() {
        String messageKeyword = keyword;
        String messageMood = mood.toString();
        System.out.println("clicked");
        if (mood == null) {
            Clients.showNotification("You need to select a mood.",
                    "warning", null, "middle_center", 0);
        } else if (keyword == null){
            Clients.showNotification("You need to enter a search promt.",
                    "warning", null, "middle_center", 0);
        } else {
            pagedList = imageService.searchByKeywordAndMood(keyword, mood, activePage, 8);
            if (pagedList.isEmpty()) {
                System.out.println("nothing");
                nothing = "There were no images found with the search prompt: " + messageKeyword + " and mood: " + messageMood;
            } else {
                System.out.println("something");
                nothing = null;
            }
        }
    }


    @Command
    public void redirectToSearchPage(@BindingParam("keyword") String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            Executions.sendRedirect("/search.zul?keyword=" + keyword);
        }
    }

    @Command
    @NotifyChange("pagedList")
    public void goToPage(@BindingParam("page") int page) {
        activePage = page - 1;
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