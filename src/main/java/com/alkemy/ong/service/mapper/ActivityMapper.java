package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityPageResponse;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.model.Activity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ActivityMapper {

    public Activity updateEntity (ActivityRequestDTO request, Activity entityFromDB){
        return Activity.builder()
                .id(entityFromDB.getId())
                .name(request.getName())
                .image(request.getImage())
                .content(request.getContent())
                .createAt(entityFromDB.getCreateAt())
                .updateAt(new Date())
                .build();
    }

    public ActivityResponseDTO entityToResponseDTO (Activity activity){

        return ActivityResponseDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .image(activity.getImage())
                .content(activity.getContent())
                .updateDate(activity.getUpdateAt())
                .build();

    }

    public Activity requestDTOToEntity (ActivityRequestDTO activityRequestDTO){
        return Activity.builder()
                .name(activityRequestDTO.name)
                .content(activityRequestDTO.content)
                .image(activityRequestDTO.image)
                .createAt(new Date())
                .build();
    }

    private List<ActivityResponseDTO> entityListToResponseDTO(List<Activity> activities) {
        List<ActivityResponseDTO> activityResponseDTOList = new ArrayList<>();
        activities.forEach(p -> activityResponseDTOList.add(entityToResponseDTO(p)));
        return activityResponseDTOList;
    }

    public ActivityPageResponse buildPageResponse(List<Activity> activities, String previousUrl, String nextUrl) {
        return ActivityPageResponse.builder()
                .activities(entityListToResponseDTO(activities))
                .nextUrl(nextUrl)
                .previousUrl(previousUrl)
                .build();
    }

}
