package goormthonUniv.floating.response;

import goormthonUniv.floating.domain.TalkSubject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TalkSubjectResponse {

    private List<TalkSubject> talkSubjects;
    private int status;
    private String message;

    public TalkSubjectResponse(List<TalkSubject> talkSubjects, int status, String message) {
        this.talkSubjects = talkSubjects;
        this.status = status;
        this.message = message;
    }
}
