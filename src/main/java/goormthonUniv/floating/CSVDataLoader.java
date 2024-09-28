package goormthonUniv.floating;

import com.opencsv.CSVReader;
import goormthonUniv.floating.domain.BalanceGame;
import goormthonUniv.floating.domain.Category;
import goormthonUniv.floating.domain.MiniGame;
import goormthonUniv.floating.domain.TalkSubject;
import goormthonUniv.floating.repository.BalanceGameRepository;
import goormthonUniv.floating.repository.MiniGameRepository;
import goormthonUniv.floating.repository.TalkSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
@RequiredArgsConstructor
public class CSVDataLoader implements CommandLineRunner {

    private final BalanceGameRepository balanceGameRepository;
    private final TalkSubjectRepository talkSubjectRepository;
    private final MiniGameRepository miniGameRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CSVDataLoader is running...");
        try(Reader reader = new InputStreamReader(new ClassPathResource("csv/BalanceGame.csv").getInputStream());
                CSVReader csvReader = new CSVReader(reader)){

            String[] line;
            while((line = csvReader.readNext())!=null){
                String questionA = line[0].replace("\uFEFF", "").trim(); // String 그대로 처리
                String questionB = line[1].trim();

                BalanceGame balanceGame = new BalanceGame();
                balanceGame.setQuestionA(questionA);
                balanceGame.setQuestionB(questionB);
                balanceGameRepository.save(balanceGame);
            }
        }
        try(Reader reader = new InputStreamReader(new ClassPathResource("csv/TalkSubject.csv").getInputStream());
                CSVReader csvReader = new CSVReader(reader)){

            String[] line;
            while((line = csvReader.readNext())!=null){
                String subject = line[0].replace("\uFEFF", "").trim(); // String 그대로 처리
                String description = line[1].trim();
                String categoryStr = line[2].trim();
                Category category = Category.fromString(categoryStr);

                TalkSubject talkSubject = new TalkSubject();
                talkSubject.setSubject(subject);
                talkSubject.setDescription(description);
                talkSubject.setCategory(category);

                talkSubjectRepository.save(talkSubject);

            }
        }
        try(Reader reader = new InputStreamReader(new ClassPathResource("csv/MiniGame.csv").getInputStream());
                CSVReader csvReader = new CSVReader(reader)){

            String[] line;
            while((line = csvReader.readNext())!=null){
                String name = line[0].replace("\uFEFF", "").trim(); // String 그대로 처리
                String description = line[1].trim();

                MiniGame miniGame = new MiniGame();
                miniGame.setName(name);
                miniGame.setDescription(description);

                miniGameRepository.save(miniGame);
            }

        }

    }
}
