package com.yet.project.web.dto.response.item;

import com.yet.project.web.dto.item.Image;
import lombok.Data;

import java.util.List;

@Data
public class ImageList {
    private List<Image> imageList;
}
