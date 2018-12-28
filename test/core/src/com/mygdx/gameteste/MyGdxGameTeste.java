package com.mygdx.gameteste;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Color;
import java.util.Random;

public class MyGdxGameTeste extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaro;
	private Texture fundo;
	private Texture canoBot;
	private Texture canoTop;
	private Circle passaroCirculo;
	private Texture gameOver;
	private Rectangle canoTopo;
	private Rectangle canoBaixo;
    private Random numeroRandomico;
    private BitmapFont font;
    private BitmapFont mensagem;
    private Boolean marcouPonto = false;
    /*private ShapeRenderer shape;*/

	private float variacao = 0;
	private float velocidadeQueda =0;
	private float deltaTima;
	private float alturaEntreCanosRandomica;
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;

    private int movimento=0;
	private int espacoEntreCanos = 280;
	private int estadoJogo = 0;
	private int pontuacao =0;

	/*camera*/
	private OrthographicCamera camera;
	private Viewport viewport;
	private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;

	@Override
	public void create () {
	    numeroRandomico = new Random();
	    passaroCirculo = new Circle();
	    /*canoTopo = new Rectangle();
	    canoBaixo = new Rectangle();*/
		batch = new SpriteBatch();
		passaro = new Texture[4];
        /*shape = new ShapeRenderer();*/

		font = new BitmapFont();
		font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font.getData().setScale(6);

		mensagem = new BitmapFont();
		mensagem.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		mensagem.getData().setScale(3);

		passaro[0] = new Texture("passaro1.png");
        passaro[1] = new Texture("passaro2.png");
        passaro[2] = new Texture("passaro3.png");
        passaro[3] = new Texture("passaro2.png");

        /*CONFIGURAÇÃO DA CAMERA*/
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH /2, VIRTUAL_HEIGHT /2, 0);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);


        canoBot = new Texture("cano_baixo_maior.png");
        canoTop = new Texture("cano_topo_maior.png");
        gameOver = new Texture("game_over.png");
		fundo = new Texture("fundo.png");

        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;
        posicaoInicialVertical =  alturaDispositivo / 2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
	}

	@Override
	public void render () {

	    camera.update();

	    //limpar frames anteriores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        deltaTima = Gdx.graphics.getDeltaTime();
        variacao += deltaTima * 10;
        //repetcao da animacao do passaro
        if (variacao > 3) variacao = 0;

	    if(estadoJogo == 0){
            if(Gdx.input.justTouched()){
                estadoJogo = 1;
                velocidadeQueda = -17;
            }
        }else {
            //velocidade queda passaro
            velocidadeQueda++;
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
                posicaoInicialVertical -= velocidadeQueda;

	        if(estadoJogo == 1){

	            //movimento canos
                posicaoMovimentoCanoHorizontal -= deltaTima * 450;

                //quanto o passo sobe ao Touch
                if (Gdx.input.justTouched()) velocidadeQueda = -17;

                //Cano sair da tela por completo
                if (posicaoMovimentoCanoHorizontal < -canoTop.getWidth()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaEntreCanosRandomica = numeroRandomico.nextInt(410) - 205;
                    marcouPonto = false;
                }
                //pontuacao
                if(posicaoMovimentoCanoHorizontal < 120 ){
                    if(!marcouPonto){
                        pontuacao ++;
                        marcouPonto = true;
                    }
                }
            }else{//game over

	            estadoJogo = 0;
	            pontuacao = 0;
	            velocidadeQueda = 0;
	            posicaoInicialVertical = alturaDispositivo /2;
	            posicaoMovimentoCanoHorizontal = larguraDispositivo;

            }


        }
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
	    batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
	    batch.draw(canoTop, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
	    batch.draw(canoBot, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBot.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
	    batch.draw(passaro[(int) variacao], 120, posicaoInicialVertical);
        font.draw(batch, String.valueOf(pontuacao),larguraDispositivo/2, alturaDispositivo - 60);
	    if(estadoJogo == 2){
	        batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
	        mensagem.draw(batch, "Toque para recomeçar", larguraDispositivo / 2 - 220 , alturaDispositivo /2 - gameOver.getHeight() / 2);
        }
        batch.end();

	    //colisao passaro
	    passaroCirculo.set(120 + passaro[0].getWidth()/2 ,posicaoInicialVertical + passaro[0].getHeight()/2,passaro[0].getWidth()/2);

	    //colisao cano baixo
	    canoBaixo = new Rectangle(
                posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - canoBot.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                canoBot.getWidth(),canoBot.getHeight()
        );

	    //colisao cano topo
	    canoTopo = new Rectangle(
                posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                canoTop.getWidth(),canoTop.getHeight()
        );


	    //desenho colisao
        /*shape.begin( ShapeRenderer.ShapeType.Filled );
        shape.circle(passaroCirculo.x,passaroCirculo.y,passaroCirculo.radius);
        shape.rect(canoBaixo.x,canoBaixo.y,canoBaixo.width,canoBaixo.height);
        shape.rect(canoTopo.x,canoTopo.y,canoTopo.width,canoTopo.height);
        shape.setColor(com.badlogic.gdx.graphics.Color.RED);
        shape.end();*/

        //teste colisao
        if(Intersector.overlaps(passaroCirculo, canoBaixo) || Intersector.overlaps( passaroCirculo,canoTopo ) ||
                posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo){
            estadoJogo = 2;
        }
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}
