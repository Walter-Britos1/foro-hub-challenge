package com.aluracursos.forohub.domain.response;

import com.aluracursos.forohub.domain.response.dto.CreateResponseDTO;
import com.aluracursos.forohub.domain.response.dto.UpdatingResponseDTO;
import com.aluracursos.forohub.domain.topic.Topic;
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
@Table(name = "responses")
@Entity(name = "Response")
@EqualsAndHashCode(of = "id")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @Column(name = "date_creating")
    private LocalDateTime dateCreating;

    @Column(name = "last_updating")
    private LocalDateTime lastUpdating;

    private Boolean solutions;
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Response(CreateResponseDTO createResponseDTO, User user, Topic topic) {
        this.message = createResponseDTO.message();
        this.dateCreating = LocalDateTime.now();
        this.lastUpdating = LocalDateTime.now();
        this.solutions = false;
        this.deleted = false;
        this.user = user;
        this.topic = topic;
    }

    public void UpdatingResponse(UpdatingResponseDTO updatingResponseDTO) {
        if (updatingResponseDTO.message() != null) {
            this.message = updatingResponseDTO.message();
        }

        if (updatingResponseDTO.solutions() != null) {
            this.solutions = updatingResponseDTO.solutions();
        }

        this.lastUpdating = LocalDateTime.now();
    }

    public void deleteResponse() {
        this.deleted = true;
    }
}
