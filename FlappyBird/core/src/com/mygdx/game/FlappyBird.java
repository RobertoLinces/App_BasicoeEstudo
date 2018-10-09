package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;

	private Texture fundo;
	private Texture chao;
	private Texture canoDeBaixo;
	private Texture canoDoAlto;
	private Texture telaFim;
	private Random numeroRandomico;
	private BitmapFont fonte;
	private BitmapFont reiniciar;
	private Circle passaroCirculo;
	private Rectangle canoTopo;
	private Rectangle canoBaixo;
	//private ShapeRenderer shape;


	//Atributos de Configuracao
	//private int movimento = 0;
	private int larguraDispositivo;
	private int alturaDispositivo;
	private int estadoDoJogo = 0;// 0 - > jogo não iniciado / 1 - > jogo iniciado 2 -> Jogo Tela Game Over
	private int pontuacao = 0;


	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical = 0;
	private float posicaoMovimentoCanoHorizontal;
	private float posicaoMovimentoCanoVertical;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;
	private boolean marcouPonto = false;

	//Movimentando o chao
	private float posicaoMovimentoChao;


	@Override
	public void create () {

		//INICIANDO VARIAVEIS DE COLISAO
		passaroCirculo = new Circle();
		canoBaixo = new Rectangle();
		canoTopo = new Rectangle();

		//shape = new ShapeRenderer();

		//LETRA DO SCORE DO JOGO
		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(3);

		reiniciar = new BitmapFont();
		reiniciar.setColor(Color.WHITE);
		reiniciar.getData().setScale(2);

		batch = new SpriteBatch();
		//passaro = new Texture("bird.png");
		passaros = new Texture[9];
		passaros[0] = new Texture("gaviao1.png");
		passaros[1] = new Texture("gaviao2.png");
		passaros[2] = new Texture("gaviao3.png");
		passaros[3] = new Texture("gaviao4.png");
		passaros[4] = new Texture("gaviao5.png");
		passaros[5] = new Texture("gaviao6.png");
		passaros[6] = new Texture("gaviao7.png");
		passaros[7] = new Texture("gaviao8.png");
		passaros[8] = new Texture("gaviao9.png");

		fundo = new Texture("bg.png");
		chao = new Texture("ground.png");

		telaFim = new Texture("gameover.png");

		canoDeBaixo = new Texture("bottomtube.png");
		canoDoAlto = new Texture("toptube.png");

		numeroRandomico = new Random();

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		posicaoInicialVertical = alturaDispositivo/2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		posicaoMovimentoCanoVertical = alturaDispositivo/2 + 45;
		espacoEntreCanos = 190;
		posicaoMovimentoChao = 0;
	}

	@Override
	public void render () {

		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 8;

		if (variacao > 9) {
			variacao = 0;
		}


		if(estadoDoJogo == 0){// JOGO NAO INICIADO

			if(Gdx.input.justTouched()){

				estadoDoJogo = 1;
			}
		}else { // JOGO INICIADO

			velocidadeQueda += deltaTime * 8;

			if (posicaoInicialVertical > 90 || velocidadeQueda < 0) {
				posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

			}



			if(estadoDoJogo == 1){


				posicaoMovimentoCanoHorizontal -= deltaTime * 200;

				//Movimentando o chao
				posicaoMovimentoChao -= deltaTime * 300;

				if (Gdx.input.justTouched()) {

					velocidadeQueda = -5f;
				}

				//VERIFICA SE O CANO SAIU DA TELA
				if (posicaoMovimentoCanoHorizontal < -304) {

					posicaoMovimentoCanoHorizontal = larguraDispositivo;
					alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 300;
					marcouPonto = false;

				}

				//VERIFICA SE O CHAO SAIU DA TELA
				if (posicaoMovimentoChao < -1000) {

					posicaoMovimentoChao = 0;
				}

				//VERIFICA PONTUACAO
				if(posicaoMovimentoCanoHorizontal < 120 ){

					if(!marcouPonto){

						pontuacao++;
						marcouPonto = true;

					}

				}

				
			}else {//TELA DE GAME OVER

				if (Gdx.input.justTouched()){

					estadoDoJogo = 0;
					pontuacao = 0;
					velocidadeQueda = 0;
					posicaoInicialVertical = alturaDispositivo/2;
					posicaoMovimentoCanoHorizontal = larguraDispositivo;

				}

			}

		}

		batch.begin();

		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);

		batch.draw(canoDoAlto, posicaoMovimentoCanoHorizontal , posicaoMovimentoCanoVertical + espacoEntreCanos + alturaEntreCanosRandomica, 62 ,600);
		batch.draw(canoDeBaixo,posicaoMovimentoCanoHorizontal,posicaoMovimentoCanoVertical - canoDeBaixo.getHeight() - 80 - espacoEntreCanos + alturaEntreCanosRandomica ,62,600);

		//TESTE PARA INSERIR MAIS CANOS
		batch.draw(canoDoAlto, posicaoMovimentoCanoHorizontal+250 , posicaoMovimentoCanoVertical + espacoEntreCanos + alturaEntreCanosRandomica, 62 ,600);
		batch.draw(canoDeBaixo,posicaoMovimentoCanoHorizontal+250,posicaoMovimentoCanoVertical - canoDeBaixo.getHeight() - 80 - espacoEntreCanos + alturaEntreCanosRandomica ,62,600);



		batch.draw(chao,posicaoMovimentoChao,0,Gdx.graphics.getWidth() + 1200, 100);

		batch.draw(passaros [ (int) variacao ], 120, posicaoInicialVertical ,90,75);

		fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo/2,alturaDispositivo - 150);

		if(estadoDoJogo == 2){

			batch.draw(telaFim, larguraDispositivo/2 - telaFim.getWidth()/2,alturaDispositivo/2, 200, 110);
			reiniciar.draw(batch,"Toque na tela para reiniciar!",larguraDispositivo/2 - 190,alturaDispositivo/2 - telaFim.getHeight()/2);
		}

		batch.end();

		passaroCirculo.set(120 + passaros[0].getWidth()/2, posicaoInicialVertical + passaros[0].getHeight()/2 , passaros[0].getWidth()/2 - 10);
		canoBaixo = new Rectangle(posicaoMovimentoCanoHorizontal, posicaoMovimentoCanoVertical - canoDeBaixo.getHeight() - 80 - espacoEntreCanos + alturaEntreCanosRandomica , 62, 600 );
		canoTopo = new Rectangle(posicaoMovimentoCanoHorizontal, posicaoMovimentoCanoVertical + espacoEntreCanos + alturaEntreCanosRandomica, 62 ,600 );


		//Desenhar formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.circle(passaroCirculo.x, passaroCirculo.y,passaroCirculo.radius);
		shape.rect(canoBaixo.x, canoBaixo.y,canoBaixo.width, canoBaixo.height);
		shape.rect(canoTopo.x, canoTopo.y, canoTopo.width, canoTopo.height);
		shape.setColor(Color.RED);
		shape.end();*/

		//TESTE DE COLISAO

		if(Intersector.overlaps(passaroCirculo,canoBaixo) || Intersector.overlaps(passaroCirculo, canoTopo) || posicaoInicialVertical <= 100 || posicaoInicialVertical >= alturaDispositivo ){

			estadoDoJogo = 2;

		}

	}


}
