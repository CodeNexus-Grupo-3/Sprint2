package school.sptech.model;

public class Dados {

    private Integer duracao;
    private Integer totalBaron;
    private Integer totalDrag;
    private Integer totalTorres;
    private Integer totalAbates;
    private Integer totalMortes;
    private Integer totalAssistencias;
    private Integer totalGold;
    private Integer totalDano;

    public Dados(
            Integer duracao,
            Integer totalBaron,
            Integer totalDrag,
            Integer totalTorres,
            Integer totalAbates,
            Integer totalMortes,
            Integer totalAssistencias,
            Integer totalGold,
            Integer totalDano
    ) {
        this.duracao = duracao;
        this.totalBaron = totalBaron;
        this.totalDrag = totalDrag;
        this.totalTorres = totalTorres;
        this.totalAbates = totalAbates;
        this.totalMortes = totalMortes;
        this.totalAssistencias = totalAssistencias;
        this.totalGold = totalGold;
        this.totalDano = totalDano;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public Integer getTotalBaron() {
        return totalBaron;
    }

    public Integer getTotalDrag() {
        return totalDrag;
    }

    public Integer getTotalTorres() {
        return totalTorres;
    }

    public Integer getTotalAbates() {
        return totalAbates;
    }

    public Integer getTotalAssistencias() {
        return totalAssistencias;
    }

    public Integer getTotalMortes() {
        return totalMortes;
    }

    public Integer getTotalGold() {
        return totalGold;
    }

    public Integer getTotalDano() {
        return totalDano;
    }

    @Override
    public String toString() {
        return "Dados{" +
                "duracao='" + duracao + '\'' +
                ", totalBaron=" + totalBaron +
                ", totalDrag=" + totalDrag +
                ", totalTorres=" + totalTorres +
                ", totalAbates=" + totalAbates +
                ", totalMortes=" + totalMortes +
                ", totalAssistencias=" + totalAssistencias +
                ", totalGold=" + totalGold +
                ", totalDano=" + totalDano +
                '}';
    }
}