package com.example.viewModel;

import com.example.dto.ShowcaseDTO;
import com.example.service.ImageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
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
public class TestViewModel {


}
