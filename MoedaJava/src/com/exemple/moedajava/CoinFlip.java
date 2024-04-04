import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.util.Random;

public class CoinFlip extends Application {

    private ImageView moedaImageView;
    private Image caraImage;
    private Image coroaImage;
    private Random random = new Random();
    private int alternancias = 0;
    private int ultimoResultado = -1; // -1 indica que ainda não houve resultado

    @Override
    public void start(Stage primaryStage) {
        // Carregar as imagens de cara e coroa
        caraImage = new Image("cara.png");
        coroaImage = new Image("coroa.png");

        // Inicializar a ImageView com a imagem padrão (cara)
        moedaImageView = new ImageView(caraImage);

        // Criar uma transição de translação para a moeda
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), moedaImageView); // Duração de 0.5 segundos
        translateTransition.setByY(-800); // Transladar a moeda para cima (máximo de -200 unidades)

        // StackPane para conter a ImageView da moeda
        StackPane root = new StackPane();
        root.getChildren().add(moedaImageView);
        root.setAlignment(Pos.CENTER);

        // Girar a moeda quando a cena for clicada
        root.setOnMouseClicked(event -> {
            if (!translateTransition.getStatus().equals(TranslateTransition.Status.RUNNING)) {
                alternancias = 0; // Reinicia as alternâncias
                ultimoResultado = -1; // Reinicia o último resultado
                girarMoeda(translateTransition);
            }
        });

        // Criar a cena
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("Coin Flip");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void girarMoeda(TranslateTransition translateTransition) {
        translateTransition.setByY(-800); // Transladar a moeda para cima (máximo de -200 unidades)
        translateTransition.setOnFinished(e -> {
            alternancias++; // Incrementa o número de alternâncias
            int resultado;
            do {
                resultado = random.nextInt(2); // Escolhe um número aleatório entre 0 e 1
            } while (resultado == ultimoResultado); // Evita a repetição do último resultado
            ultimoResultado = resultado; // Atualiza o último resultado
            if (resultado == 0) {
                moedaImageView.setImage(caraImage); // Mostra cara
            } else {
                moedaImageView.setImage(coroaImage); // Mostra coroa
            }
            translateTransition.setByY(160); // Transladar a moeda para baixo (máximo de 200 unidades)
            if (alternancias < 6) {
                translateTransition.play(); // Se não completou as 6 alternâncias, continua a transição
            } else {
                System.out.println("Resultado: " + (resultado == 0 ? "Cara" : "Coroa")); // Imprime o resultado final
            }
        });
        translateTransition.play(); // Inicia a transição de translação
    }

    public static void main(String[] args) {
        launch(args);
    }
}
