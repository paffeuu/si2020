package si.lista2.model.jolka;

public class SolvedJolka extends Jolka {
    public SolvedJolka(Jolka jolka) {
        super(jolka.id, jolka.words, jolka.gaps, jolka.stage);
        this.stage = new char[jolka.stage.length][jolka.stage[0].length];
        for (int i = 0; i < jolka.stage.length; i++) {
            for (int j = 0; j < jolka.stage[0].length; j++) {
                this.stage[i][j] = jolka.stage[i][j];
            }
        }

    }
}
