package com.aluracursos.forohub.domain.topic;

import com.aluracursos.forohub.domain.course.Course;
import com.aluracursos.forohub.domain.topic.dto.CreateTopicDTO;
import com.aluracursos.forohub.domain.topic.dto.UpdateTopicDTO;
import com.aluracursos.forohub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
@Entity(name = "Topic")
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "last_updating")
    private LocalDateTime lastUpdating;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Topic(CreateTopicDTO createTopicDTO, User user, Course course) {
        this.title = createTopicDTO.title();
        this.message = createTopicDTO.message();
        this.dateCreation = LocalDateTime.now();
        this.lastUpdating = LocalDateTime.now();
        this.status = Status.OPEN;
        this.user = user;
        this.course = course;
    }

    public void UpdateTopicWhitCourse(UpdateTopicDTO updateTopicDTO, Course course) {
        if (updateTopicDTO.title() != null){
            this.title = updateTopicDTO.title();
        }
        if (updateTopicDTO.message() != null){
            this.message = updateTopicDTO.message() ;
        }
        if (updateTopicDTO.status() != null){
            this.status = updateTopicDTO.status();
        }
        if (updateTopicDTO.courseId() != null){
            this.course = course;
        }
        this.lastUpdating = LocalDateTime.now();
    }

    public void UpdateTopic(UpdateTopicDTO updateTopicDTO){
        if (updateTopicDTO.title() != null){
            this.title = updateTopicDTO.title();
        }
        if (updateTopicDTO.message() != null){
            this.message = updateTopicDTO.message();
        }
        if(updateTopicDTO.status() != null){
            this.status = updateTopicDTO.status();
        }
        this.lastUpdating = LocalDateTime.now();
    }

    public void deleteTopic(){
        this.status = Status.DELETED;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
