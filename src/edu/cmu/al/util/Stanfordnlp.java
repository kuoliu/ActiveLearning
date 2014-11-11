package edu.cmu.al.util;

import java.util.*;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class Stanfordnlp {

	private static StanfordCoreNLP stanfordnlp = null;

	static {
		Properties props = new Properties();
		props.put("annotators", Configuration.getStanfordnlpComponent());
		stanfordnlp = new StanfordCoreNLP(props);
	}

	public static Annotation AnnotateDoc(String doc) {
		Annotation document = new Annotation(doc);
		stanfordnlp.annotate(document);
		return document;
	}

	public static List<CoreMap> getSentences(Annotation document) {
		return document.get(SentencesAnnotation.class);
	}

	public static ArrayList<Chunk> annotatePOSandNE(CoreMap sentence) {
		ArrayList<Chunk> wordAnnotation = new ArrayList<Chunk>();
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
			String word = token.get(TextAnnotation.class);
			String pos = token.get(PartOfSpeechAnnotation.class);
			String ne = token.get(NamedEntityTagAnnotation.class);
			Chunk chunk = new Chunk(word, pos, ne);
			wordAnnotation.add(chunk);
		}
		return wordAnnotation;
	}

	public static ArrayList<Chunk> annotatePOS(CoreMap sentence) {
		ArrayList<Chunk> wordAnnotation = new ArrayList<Chunk>();
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
			String word = token.get(TextAnnotation.class);
			String pos = token.get(PartOfSpeechAnnotation.class);
			Chunk chunk = new Chunk(word, pos, "");
			wordAnnotation.add(chunk);
		}
		return wordAnnotation;
	}

	public static Tree parseSentence(CoreMap sentence) {
		return sentence.get(TreeAnnotation.class);
	}

	/* annotate phrases */
	private static ArrayList<String> getPhrases(Tree parse, String type) {
		ArrayList<String> phraseList = new ArrayList<String>();
		for (Tree subtree : parse) {
			if (subtree.label().value().equals(type)) {
				StringBuilder sb = new StringBuilder();
				for (Tree tree : subtree.preOrderNodeList()) {
					if (tree.isLeaf())
						sb.append(tree.nodeString() + " ");
				}
				phraseList.add(sb.toString().substring(0, sb.length() - 1)
						.trim());
			}
		}
		return phraseList;
	}

	public static ArrayList<String> getNounPhrases(Tree parse) {
		return getPhrases(parse, "NP");
	}

	public static ArrayList<String> getVerbPhrases(Tree parse) {
		return getPhrases(parse, "VP");
	}

	public static ArrayList<String> getPrepPhrases(Tree parse) {
		return getPhrases(parse, "PP");
	}

	public static ArrayList<String> getAdjPhrases(Tree parse) {
		return getPhrases(parse, "ADJP");
	}

	public static ArrayList<String> getAdvPhrases(Tree parse) {
		return getPhrases(parse, "ADVP");
	}

	public static boolean isNeg(CoreMap sentence){
		SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
		for(SemanticGraphEdge edge : dependencies.edgeIterable()){
			if(edge.getRelation().getShortName().equals("neg"))
				return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String str = "Richard Liu is a graduate student from CMU.";
		Annotation document = Stanfordnlp.AnnotateDoc(str);
		List<CoreMap> sentences = Stanfordnlp.getSentences(document);
		for (CoreMap sentence : sentences) {
			List<Chunk> posAndNe = Stanfordnlp.annotatePOSandNE(sentence);
			Tree parseTree = Stanfordnlp.parseSentence(sentence);
			System.out.println(posAndNe);
			System.out.println(parseTree);
			System.out.println(Stanfordnlp.getNounPhrases(parseTree));
		}
	}
}
