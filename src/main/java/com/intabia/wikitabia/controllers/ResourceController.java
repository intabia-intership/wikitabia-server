package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.dto.TagDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.wrappers.RequestInfoSaver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.intabia.wikitabia.dto.ResourceModel;
import com.intabia.wikitabia.services.interfaces.ResourceService;
import com.intabia.wikitabia.services.interfaces.TagsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resource")
@AllArgsConstructor
public class ResourceController {
  private ResourceService resourceService;
  private TagsService tagsService;
  private RequestInfoSaver info;

  @GetMapping("/create")
  public String setModelAttributeForCreating(Model model) {
    model.addAttribute("resource", new ResourceModel());
    return "/CreateResourceForm";
  }

  @PostMapping("/create")
  public String createResource(HttpSession session, @ModelAttribute("resource") @Valid ResourceModel resourceModel,
                               BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "/CreateResourceForm";
    }
    UserDto user = (UserDto) session.getAttribute("user");
    resourceModel.setCreator(user);
    ResourceDto resourceDto = createResourceDtoFromModel(resourceModel);

    resourceService.createResource(resourceDto);
    return "redirect:/Index.jsp";
  }

  private ResourceDto createResourceDtoFromModel(ResourceModel resourceModel) {
    List<TagDto> tags = formatTags(resourceModel.getTags());

    ResourceDto resourceDto = new ResourceDto();
    resourceDto.setId(resourceModel.getId());
    resourceDto.setName(resourceModel.getName());
    resourceDto.setUrl(resourceModel.getUrl());
    resourceDto.setCreator(resourceModel.getCreator());
    resourceDto.setTags(tags);
    return resourceDto;
  }

  @GetMapping("/update")
  public String setModelAttributeForUpdating(Model model) {
    model.addAttribute("resource", new ResourceModel());
    return "UpdateResourceForm";
  }

  @PostMapping("/update")
  public String updateResource(@Valid @ModelAttribute("resource") ResourceModel resourceModel,
                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "UpdateResourceForm";
    }
    ResourceDto resource = createResourceDtoFromModel(resourceModel);
    resourceService.updateResource(resource);
    return "redirect:/Index.jsp";
  }

  @GetMapping("/delete")
  public String setModelForDeleting(Model model) {
    model.addAttribute("resource", new ResourceModel());
    return "DeleteResourceForm";
  }

  @PostMapping("/delete")
  public String deleteResource(ResourceModel resourceModel) {
    resourceService.deleteResource(resourceModel.getId());
    return "redirect:/Index.jsp";
  }

  @GetMapping("/view")
  public String viewResources(HttpServletRequest request, Model model) {
    updateRequestInfo(request);
    Page<ResourceDto> resources = resourceService.getResources(info.getPage(), info.getSize(),
        info.getSort(), info.getFilterByName(), info.getFilterByTag());
    List<String> pageRefs = buildPageRefs(resources.getTotalPages());

    model.addAttribute("resources", resources.getContent());
    model.addAttribute("pageRefs", pageRefs);
    model.addAttribute("tags", tagsService.getTags());
    return "ViewResources";
  }

  private void updateRequestInfo(HttpServletRequest request) {
    String pageFromRequest = request.getParameter("page");
    String sizeFromRequest = request.getParameter("size");
    String sortFromRequest = request.getParameter("sort");
    String filterByNameFromRequest = request.getParameter("filterByName");
    String filterByTagFromRequest = request.getParameter("filterByTag");
    if (pageFromRequest != null) {
      info.setPage(Integer.parseInt(pageFromRequest));
    }
    if (sizeFromRequest != null) {
      info.setSize(Integer.parseInt(sizeFromRequest));
    }
    if (sortFromRequest != null) {
      info.setSort(sortFromRequest);
    }
    if (filterByNameFromRequest != null) {
      info.setFilterByName(filterByNameFromRequest);
    }
    if (filterByTagFromRequest != null) {
      List<String> formattedTagsNames =
          List.of(filterByTagFromRequest.replaceAll("\\s+", "").toLowerCase().split(","));
      info.setFilterByTag(formattedTagsNames);
    }
  }

  private List<String> buildPageRefs(int totalPages) {
    List<String> refs = new ArrayList<>();
    for (int i = 1; i <= totalPages + 1; i++) {
      String ref = "<a href='view?page=" + (i - 1)
          + "&size=" + info.getSize()
          + "&sort=" + info.getSort()
          + "&filterByName=" + info.getFilterByName()
          + "&filterByTags=" + info.getFilterByTag()
          + "' methods='get'>" + i + "</a>  ";
      refs.add(ref);
    }
    return refs;
  }

  private List<TagDto> formatTags(String tags) {
    List<TagDto> tagDto = null;
    if (!tags.equals("")) {
      String[] parsedTags = tags.replaceAll("\\s+", "").toLowerCase().split(",");
      tagDto = Arrays.stream(parsedTags)
          .map(TagDto::new)
          .collect(Collectors.toList());
    }
    return tagDto;
  }
}
