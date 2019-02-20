package net.tfobz.smalltalk;

public class Voting {
	private String line;
	private int[] votes;

	public Voting(String line) {
		this.line = line;
		votes = new int[4];
	}

	public void setVote(int vote) {
		votes[vote]++;
	}

	public String getVotingString() {
		String lh[] = new String[5];
		System.out.println("     " + line);
		String line1 = line.substring(9);
		for (int i = 0; i < 5; i++) {
			int j = line1.indexOf("///");
			lh[i] = line1.substring(0, j);
			line1 = line1.substring(j + 3);
		}

		return "=)(/&%$§!" + lh[0] + "///" + lh[1] + "///" + votes[0] + "///" + lh[2] + "///" + votes[1] + "///" + lh[3]
				+ "///" + votes[2] + "///" + lh[4] + "///" + votes[3];
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int[] getVotes() {
		return votes;
	}


}
